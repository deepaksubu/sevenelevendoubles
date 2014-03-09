package sevenelevendoubles.enums;

import sevenelevendoubles.DrinkingTask;
import sevenelevendoubles.Player;
import sevenelevendoubles.service.Outcome;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class GameManager {

    private Queue<Player> playersLeft;

    public GameManager(Queue<Player> playersLeft) {
        this.playersLeft = playersLeft;
    }

    public synchronized Player getCurrentPlayer() {
        return playersLeft.peek();
    }

    public synchronized Queue<Player> getPlayersLeft() {
        return playersLeft;
    }

    public synchronized void makePlayerDrink(Player player, ExecutorService executorService) {
        DrinkingTask drinkingTask = new DrinkingTask(player);
        executorService.submit(drinkingTask);
    }

    public synchronized Player selectForDrinking() {
        assert this.playersLeft.size() > 1: "The number of players should be more than 1";
        int availableSize = this.playersLeft.size() - 1;
        int chosenIndex = (int) (Math.random()* availableSize);
        int trackerIndex = 0;
        Iterator<Player> playerIterator = playersLeft.iterator();

        // skip the head
        playerIterator.next();
        while (playerIterator.hasNext()) {
            Player choosenPlayer = playerIterator.next();
            if (trackerIndex == chosenIndex) {
                System.out.println(new StringBuffer(this.getCurrentPlayer().getName()).append(" says: '").append(choosenPlayer.getName()).append(", drink!'").toString());
                return choosenPlayer;
            }
            trackerIndex++;
        }
        throw new IllegalStateException("The program is not in a valid state");
    }

    public boolean isAnyPlayerDrinking() {
        Iterator<Player> playerIterator = playersLeft.iterator();
        while (playerIterator.hasNext()) {
            if (playerIterator.next().getNoOfDrinksDrinking() > 0) {
               return true;
            }
        }
        return false;
    }

    public synchronized void removeDrunkPlayers(int max) {

        Iterator<Player> iterator = playersLeft.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getNoOfDrinksFinished() >= max) {
                System.out.println(new StringBuffer(player.getName()).append(" says: 'I've had too many.  I need to stop.'").toString());
                iterator.remove();
            }
        }
    }

    public synchronized boolean simulatePlayerTurn(Outcome outcome, ExecutorService executorService) {

        if (getPlayersLeft().size() == 1) {
            System.out.println(getCurrentPlayer().getName() + " has won");
            return false;
        }
        System.out.println(new StringBuffer("There are ").append(playersLeft.size()).append(" players").toString());
        System.out.println(new StringBuffer("It is ").append(playersLeft.peek().getName()).append("'s turn").toString());
        if (outcome.isAWin()) {
            Player player = selectForDrinking();
            makePlayerDrink(player, executorService);
        } else {
            //TODO: Add a while loop here
            if (!isAnyPlayerDrinking()) {
                Player loser = playersLeft.poll();
                playersLeft.offer(loser);
            }
        }
        return true;
    }

}
