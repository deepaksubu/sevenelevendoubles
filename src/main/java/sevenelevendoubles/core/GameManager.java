package sevenelevendoubles.core;

import sevenelevendoubles.bean.DiceRollOutput;
import sevenelevendoubles.bean.Player;
import sevenelevendoubles.bean.Result;
import sevenelevendoubles.selector.Selector;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
public class GameManager implements PlayerRemover {

    private List<Player> players;

    public GameManager(List<Player> players) {
        this.players = new CopyOnWriteArrayList<>(players);
        for (Player player: players) {
            player.setPlayerRemover(this);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void makePlayerDrink(Player player) {
        if (player != null) {
            player.startDrinking();
        }
    }

    public Player selectForDrinking(int selectedPlayerIndex) {
        if (players.size() > 1) {
            Player choosenPlayer = players.get(selectedPlayerIndex);
            System.out.println(new StringBuffer(players.get(0).getName()).append(" says: '").append(choosenPlayer.getName()).append(", drink!'").toString());
            return choosenPlayer;
        } else {
            return null;
        }
    }

    public boolean isAnyPlayerDrinking() {
       for (Player player: players) {
           if (player.getNoOfDrinksDrinking() > 0) {
               return true;
           }
       }
        return false;
    }

    public Result simulatePlayerTurn(DiceRollOutput diceRollOutput, Selector selector) {
        if (getPlayers().size() == 1) {
            System.out.println(players.get(0).getName() + " is the winner");
            return new Result(players.get(0), true);
        }
        System.out.println(new StringBuffer("There are ").append(players.size()).append(" players").toString());
        System.out.println(new StringBuffer("It is ").append(players.get(0).getName()).append("'s turn").toString());
        for (Player player: players) {
            player.printStatus();
        }
        System.out.println("\n");

        System.out.println(new StringBuffer(players.get(0).getName()).append(" rolled a ").append(diceRollOutput.getMessage()).toString());
        if (diceRollOutput.isAWin()) {
            Player player;
            try {
                player = selectForDrinking(selector.selectPlayer(players.size() - 1));
            } catch (ArrayIndexOutOfBoundsException aib) {
                // Sometimes, the choosen player maybe removed by another thread in which case just do the selection again
                player = selectForDrinking(selector.selectPlayer(players.size() - 1));
            }
            makePlayerDrink(player);
        } else {
            if (!isAnyPlayerDrinking()) {
                Player loser = players.remove(0);
                players.add(loser);
            }
        }
        return new Result(players.get(0), false);
    }

    @Override
    public Player removeFromPlayerList(Player player) {
        players.remove(player);
        return player;
    }
}
