package sevenelevendoubles.core;

import sevenelevendoubles.entity.Player;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

/**
 *
 * The game manager keeps track of the players if they are drinking too much and to check that the game is in a valid state before each throw of the dice.
 *
 * The players is a random access list which is backed by using the thread safe CopyOnWriteArrayList implementation. The two options available were either a list of a queue
 * The queue has a notion of head which is convenient for tracking the current player. It is also natural to take a player from the head of the queue and to add it to the tail.
 *
 * However, the queue is not meant for random access. Hence, the convention is that the current player is always at index 0, if the current player hands off the dice after
 * a throw, then he is inserted at the end of the list
 *
 * User: deepak
 * Date: 3/8/14
 */
public class GameManager {

    private List<Player> players;
    private final int maxDrinks;

    public GameManager(List<Player> players, int maxDrinks) {
        this.players = new CopyOnWriteArrayList<>(players);
        this.maxDrinks = maxDrinks;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void removePlayer(Player player) {
        if (players.size() > 2) {
            System.out.println(new StringBuffer(player.getName()).append(" says: 'I've had too many.  I need to stop.'").toString());
        } else if (players.size() == 2) {
            System.out.println("Waiting for " + player.getName() +" to finish");
        } else {
            throw new InvalidStateException("Game simulation is in an invalid state:" + player.getName());
        }
        this.getPlayers().remove(player);
    }

    public void makePlayerDrink(Player player, ExecutorService executorService) {
        DrinkingTask drinkingTask = new DrinkingTask(player, this);
        executorService.execute(drinkingTask);
    }

    public Player selectForDrinking(int selectedPlayerIndex) {
        assert this.players.size() > 1: "The number of players should be more than 1";
        Player choosenPlayer = players.get(selectedPlayerIndex);
        System.out.println(new StringBuffer(players.get(0).getName()).append(" says: '").append(choosenPlayer.getName()).append(", drink!'").toString());
        return choosenPlayer;

    }

    public boolean isAnyPlayerDrinking() {
       for (Player player: players) {
           if (player.getNoOfDrinksDrinking() > 0) {
               return true;
           }
       }
        return false;
    }

    public boolean simulatePlayerTurn(DiceThrowResult diceThrowResult, ExecutorService executorService) {
        if (getPlayers().size() == 1) {
            System.out.println(players.get(0).getName() + " is the winner");
            return false;
        }
        System.out.println(new StringBuffer("There are ").append(players.size()).append(" players").toString());
        System.out.println(new StringBuffer("It is ").append(players.get(0).getName()).append("'s turn").toString());
        for (Player player: players) {
            player.printStatus();
        }
        System.out.println("\n");

        System.out.println(new StringBuffer(players.get(0).getName()).append(" rolled a ").append(diceThrowResult.getMessage()).toString());
        if (diceThrowResult.isAWin()) {
            Player player = selectForDrinking(new RandomizedSelector().selectPlayer(players.size() - 1));
            makePlayerDrink(player, executorService);
        } else {
            if (!isAnyPlayerDrinking()) {
                Player loser = players.remove(0);
                players.add(loser);
            }
        }
        return true;
    }

    public int getMaxDrinks() {
        return maxDrinks;
    }
}
