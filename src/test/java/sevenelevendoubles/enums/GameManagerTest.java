package sevenelevendoubles.enums;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.Player;
import sevenelevendoubles.service.Outcome;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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
            Player player = gameManager.selectForDrinking(new RandomizedSelector().selectPlayer(gameManager.getPlayersLeft().size())-1);
        }
    }

    @Test
    public void testSimulatePlayerTurn_WinningThrow() {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        Outcome doubles = new Outcome(2,2);
        gameManager.simulatePlayerTurn(doubles, Executors.newCachedThreadPool());
        Assert.assertEquals(gameManager.getPlayersLeft().get(0), Player.createPlayer("Alex", 3));
    }

    @Test
    public void testSimulatePlayerTurn_LoosingThrow() {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        Outcome doubles = new Outcome(2,3);
        gameManager.simulatePlayerTurn(doubles, Executors.newCachedThreadPool() );
        Assert.assertEquals(gameManager.getPlayersLeft().get(0), Player.createPlayer("Bob", 4));
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
        return new GameManager(players, MAX_DRINKS);
    }

    private GameManager initGameManagerSomeDrinkingPlayers() {
        List<Player> players = new CopyOnWriteArrayList<>();
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
        return new GameManager(players, MAX_DRINKS);
    }


    private Player createDrinkingPlayer(String name, int speedOfDrinking) {
        Player player = createPlayer(name, speedOfDrinking);
        player.startDrinking();
        return player;
    }

    private Player createDrunkPlayer(String name, int speedOfDrinking) {
        Player player = createPlayer(name, speedOfDrinking);
        for (int i = 0; i < MAX_DRINKS; i++) {
        player.startDrinking();
        player.endDrinking();
        }
        return player;
    }

}
