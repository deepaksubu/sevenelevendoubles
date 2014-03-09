package sevenelevendoubles.enums;

import sevenelevendoubles.Player;
import sevenelevendoubles.service.Outcome;

import java.util.Iterator;
import java.util.Queue;

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

    public synchronized Player makePlayerDrink(Player player) {
        return null;
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


    public synchronized void simulatePlayerTurn(Outcome outcome) {
        if (getPlayersLeft().size() == 1) {
            System.out.println(getCurrentPlayer() + " has won");
        }
        if (outcome.isAWin()) {
            Player player = selectForDrinking();
            makePlayerDrink(player);
        } else {
            Player loser = playersLeft.poll();
            playersLeft.offer(loser);
        }
    }

}
