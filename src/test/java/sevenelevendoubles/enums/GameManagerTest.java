package sevenelevendoubles.enums;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.Player;
import sevenelevendoubles.service.Outcome;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static sevenelevendoubles.Player.createPlayer;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class GameManagerTest {

    @Test
    public void testChoosePlayerForDrinking() throws Exception {
        GameManager gameManager = initGameManager();
        for (int i = 0; i < 100 ; i++) {
            Player player = gameManager.selectForDrinking();
        }
    }

    @Test
    public void testSimulatePlayerTurn_WinningThrow() {
        GameManager gameManager = initGameManager();
        Outcome doubles = new Outcome(2,2);
        gameManager.simulatePlayerTurn(doubles);
        Assert.assertEquals(gameManager.getPlayersLeft().peek(), Player.createPlayer("Alex", 3));
    }

    @Test
    public void testSimulatePlayerTurn_LoosingThrow() {
        GameManager gameManager = initGameManager();
        Outcome doubles = new Outcome(2,3);
        gameManager.simulatePlayerTurn(doubles);
        Assert.assertEquals(gameManager.getPlayersLeft().peek(), Player.createPlayer("Bob", 4));
    }

    private GameManager initGameManager() {
        Queue<Player> players = new ConcurrentLinkedQueue<>();
        players.add(createPlayer("Alex", 3));
        players.add(createPlayer("Bob", 4));
        players.add(createPlayer("Chris", 5));
        players.add(createPlayer("Deepak", 6));
        players.add(createPlayer("Eye", 3));
        players.add(createPlayer("Federer", 5));
        players.add(createPlayer("Garry", 5));
        players.add(createPlayer("Harry", 5));
        players.add(createPlayer("Ian", 5));
        players.add(createPlayer("Jack", 5));
        players.add(createPlayer("Kara", 5));


        return new GameManager(players);
    }

}
