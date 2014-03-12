package sevenelevendoubles.core;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.entity.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class GameManagerTest {

    public static final int MAX_DRINKS = 4;

    @Test
    public void testChoosePlayerForDrinking() throws Exception {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        for (int i = 0; i < 100 ; i++) {
            Player player = gameManager.selectForDrinking(new RandomizedSelector().selectPlayer(gameManager.getPlayers().size())-1);
        }
    }

    @Test
    public void testSimulatePlayerTurn_WinningThrow() {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        DiceThrowResult doubles = new DiceThrowResult(2,2);
        gameManager.simulatePlayerTurn(doubles, Executors.newCachedThreadPool());
        Assert.assertEquals(gameManager.getPlayers().get(0), new Player("Alex", 3));
    }

    @Test
    public void testSimulatePlayerTurn_LoosingThrow() {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        DiceThrowResult doubles = new DiceThrowResult(2,3);
        gameManager.simulatePlayerTurn(doubles, Executors.newCachedThreadPool() );
        Assert.assertEquals(gameManager.getPlayers().get(0), new Player("Bob", 4));
    }

    @Test
    public void testIsPlayerDrinking_No() {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        Assert.assertFalse(gameManager.isAnyPlayerDrinking());
    }

    @Test
    public void testIsPlayerDrinking_Yes() {
        GameManager gameManager = initGameManagerSomeDrinkingPlayers();
        Assert.assertTrue(gameManager.isAnyPlayerDrinking());
    }

    private GameManager initGameManagerWithMostlySoberPlayer() {
        List<Player> players = new CopyOnWriteArrayList<>();
        players.add(new Player("Alex", 3));
        players.add(new Player("Bob", 4));
        players.add(new Player("Chris", 5));
        players.add(new Player("Deepak", 6));
        players.add(new Player("Eye", 3));
        players.add(new Player("Federer", 5));
        players.add(new Player("Garry", 5));
        players.add(new Player("Harry", 5));
        players.add(new Player("Ian", 5));
        players.add(new Player("Jack", 5));
        players.add(new Player("Kara", 5));
        return new GameManager(players, MAX_DRINKS);
    }

    private GameManager initGameManagerSomeDrinkingPlayers() {
        List<Player> players = new CopyOnWriteArrayList<>();
        players.add(createDrinkingPlayer("Alex", 3));
        players.add(new Player("Bob", 4));
        players.add(new Player("Chris", 5));
        players.add(new Player("Deepak", 6));
        players.add(new Player("Eye", 3));
        players.add(createDrinkingPlayer("Federer", 5));
        players.add(new Player("Garry", 5));
        players.add(new Player("Harry", 5));
        players.add(new Player("Ian", 5));
        players.add(createDrinkingPlayer("Jack", 5));
        players.add(new Player("Kara", 5));
        return new GameManager(players, MAX_DRINKS);
    }


    private Player createDrinkingPlayer(String name, int speedOfDrinking) {
        Player player = new Player(name, speedOfDrinking);
        player.startDrinking(MAX_DRINKS);
        return player;
    }

}
