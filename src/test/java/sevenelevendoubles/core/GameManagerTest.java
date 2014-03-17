package sevenelevendoubles.core;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.bean.DiceRollOutput;
import sevenelevendoubles.bean.Player;
import sevenelevendoubles.selector.RandomizedSelector;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
        DiceRollOutput doubles = new DiceRollOutput(2,2);
        gameManager.simulatePlayerTurn(doubles, new RandomizedSelector());
        Assert.assertEquals(gameManager.getPlayers().get(0), new Player("Alex", 3));
    }

    @Test
    public void testMakePlayerDrink () throws InterruptedException {
        List<Player> players = new CopyOnWriteArrayList<>();

        int maxDrinks = 4;
        Player alex = new Player("Alex", 40);
        Player bob = new Player("Bob", 40);
        Player chris = new Player("Chris", 40);
        players.add(alex);
        players.add(bob);
        players.add(chris);
        GameManager gameManager = new GameManager(players);
        gameManager.makePlayerDrink(alex);
        gameManager.makePlayerDrink(alex);
        gameManager.makePlayerDrink(alex);
        gameManager.makePlayerDrink(alex);
        validatePlayerDrinkingState(alex, 4, 0, maxDrinks);
        Thread.sleep(200);
        validatePlayerDrinkingState(alex, 0, 4, maxDrinks);

        gameManager.makePlayerDrink(bob);
        gameManager.makePlayerDrink(bob);
        validatePlayerDrinkingState(bob, 2, 0, maxDrinks);
        Thread.sleep(100);
        validatePlayerDrinkingState(bob, 0, 2, maxDrinks);

        gameManager.makePlayerDrink(chris);
        gameManager.makePlayerDrink(chris);
        validatePlayerDrinkingState(chris, 2, 0, maxDrinks);
        Thread.sleep(100);
        validatePlayerDrinkingState(chris, 0, 2, maxDrinks);
    }


    private void validatePlayerDrinkingState(Player player, int expectedNoOfDrinksDrinking, int exectedNoOfDrinksFinished, int maxDrinksAllowed) {
        Assert.assertEquals(expectedNoOfDrinksDrinking, player.getNoOfDrinksDrinking());
        Assert.assertEquals(exectedNoOfDrinksFinished, player.getNoOfDrinksFinished());
    }


    @Test
    public void testSimulatePlayerTurn_LoosingThrow() {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        DiceRollOutput doubles = new DiceRollOutput(2,3);
        gameManager.simulatePlayerTurn(doubles, new RandomizedSelector());
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
        return new GameManager(players);
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
        return new GameManager(players);
    }


    private Player createDrinkingPlayer(String name, int speedOfDrinking) {
        Player player = new Player(name, speedOfDrinking);
        player.startDrinking();
        return player;
    }

}
