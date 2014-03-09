package sevenelevendoubles.enums;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.Player;
import sevenelevendoubles.service.Outcome;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import static sevenelevendoubles.Player.createPlayer;

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
            Player player = gameManager.selectForDrinking();
        }
    }

    @Test
    public void testSimulatePlayerTurn_WinningThrow() {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        Outcome doubles = new Outcome(2,2);
        gameManager.simulatePlayerTurn(doubles, Executors.newCachedThreadPool());
        Assert.assertEquals(gameManager.getPlayersLeft().peek(), Player.createPlayer("Alex", 3));
    }

    @Test
    public void testSimulatePlayerTurn_LoosingThrow() {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        Outcome doubles = new Outcome(2,3);
        gameManager.simulatePlayerTurn(doubles, Executors.newCachedThreadPool() );
        Assert.assertEquals(gameManager.getPlayersLeft().peek(), Player.createPlayer("Bob", 4));
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

    @Test
    public void testRemoveDrunkPlayers_SomeDrunkPlayers() {
        GameManager gameManager = initGameManagerSomeDrunkPlayers();
        int initSize = gameManager.getPlayersLeft().size();
        gameManager.removeDrunkPlayers(MAX_DRINKS);
        Assert.assertEquals(initSize - 3, gameManager.getPlayersLeft().size());
    }

    @Test
    public void testRemoveDrunkPlayers_NoDrunkPlayers() {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        int initSize = gameManager.getPlayersLeft().size();
        gameManager.removeDrunkPlayers(MAX_DRINKS);
        Assert.assertEquals(gameManager.getPlayersLeft().size(), initSize);
    }

    private GameManager initGameManagerWithMostlySoberPlayer() {
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

    private GameManager initGameManagerSomeDrinkingPlayers() {
        Queue<Player> players = new ConcurrentLinkedQueue<>();
        players.add(createDrinkingPlayer("Alex", 3));
        players.add(createPlayer("Bob", 4));
        players.add(createPlayer("Chris", 5));
        players.add(createPlayer("Deepak", 6));
        players.add(createPlayer("Eye", 3));
        players.add(createDrinkingPlayer("Federer", 5));
        players.add(createPlayer("Garry", 5));
        players.add(createPlayer("Harry", 5));
        players.add(createPlayer("Ian", 5));
        players.add(createDrinkingPlayer("Jack", 5));
        players.add(createPlayer("Kara", 5));
        return new GameManager(players);
    }

    private GameManager initGameManagerSomeDrunkPlayers() {
        Queue<Player> players = new ConcurrentLinkedQueue<>();
        players.add(createDrunkgPlayer("Alex", 3));
        players.add(createPlayer("Bob", 4));
        players.add(createPlayer("Chris", 5));
        players.add(createPlayer("Deepak", 6));
        players.add(createPlayer("Eye", 3));
        players.add(createDrunkgPlayer("Federer", 5));
        players.add(createPlayer("Garry", 5));
        players.add(createPlayer("Harry", 5));
        players.add(createPlayer("Ian", 5));
        players.add(createDrunkgPlayer("Jack", 5));
        players.add(createPlayer("Kara", 5));
        return new GameManager(players);
    }


    private Player createDrinkingPlayer(String name, int speedOfDrinking) {
        Player player = createPlayer(name, speedOfDrinking);
        player.startDrinking();
        return player;
    }

    private Player createDrunkgPlayer(String name, int speedOfDrinking) {
        Player player = createPlayer(name, speedOfDrinking);
        for (int i = 0; i < MAX_DRINKS; i++) {
        player.startDrinking();
        player.endDrinking();
        }
        return player;
    }

}
