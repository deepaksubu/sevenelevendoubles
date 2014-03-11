package sevenelevendoubles.enums;

import sevenelevendoubles.DrinkingTask;
import sevenelevendoubles.Player;
import sevenelevendoubles.service.Outcome;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class GameManager {

    private List<Player> playersLeft;
    private final int maxDrinks;

    public GameManager(List<Player> playersLeft, int maxDrinks) {
        this.playersLeft = new CopyOnWriteArrayList<>(playersLeft);
        this.maxDrinks = maxDrinks;
    }

    public List<Player> getPlayersLeft() {
        return playersLeft;
    }

    public void removePlayer(Player player) {
        this.getPlayersLeft().remove(player);
    }

    public void makePlayerDrink(Player player, ExecutorService executorService) {
        DrinkingTask drinkingTask = new DrinkingTask(player, this);
        executorService.execute(drinkingTask);
    }

    public Player selectForDrinking(int selectedPlayerIndex) {
        assert this.playersLeft.size() > 1: "The number of players should be more than 1";
        Player choosenPlayer = playersLeft.get(selectedPlayerIndex);
        System.out.println(new StringBuffer(playersLeft.get(0).getName()).append(" says: '").append(choosenPlayer.getName()).append(", drink!'").toString());
        return choosenPlayer;

    }

    public boolean isAnyPlayerDrinking() {
       for (Player player: playersLeft) {
           if (player.getNoOfDrinksDrinking() > 0) {
               return true;
           }
       }
        return false;
    }

    public void removeDrunkPlayers(int max) {
        for (Player player: playersLeft) {
            if (player.getNoOfDrinksFinished() >= max) {
                playersLeft.remove(player);
                System.out.println(new StringBuffer(player.getName()).append(" says: 'I've had too many.  I need to stop.'").toString());
            }
        }
    }

    public boolean simulatePlayerTurn(Outcome outcome, ExecutorService executorService) {
        if (getPlayersLeft().size() == 1) {
            System.out.println(playersLeft.get(0).getName() + " has won");
            return false;
        }
        System.out.println(new StringBuffer("There are ").append(playersLeft.size()).append(" players").toString());
        System.out.println(new StringBuffer("It is ").append(playersLeft.get(0).getName()).append("'s turn").toString());
        if (outcome.isAWin()) {
            Player player = selectForDrinking(new RandomizedSelector().selectPlayer(playersLeft.size() - 1));
            makePlayerDrink(player, executorService);
        } else {
            //TODO: Add a while loop here
            if (!isAnyPlayerDrinking()) {
                Player loser = playersLeft.remove(0);
                playersLeft.add(loser);
            }
        }
        return true;
    }

    public int getMaxDrinks() {
        return maxDrinks;
    }
}
