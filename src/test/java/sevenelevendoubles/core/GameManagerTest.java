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
        DiceRollOutput doubles = new DiceRollOutput(2,2);
        gameManager.simulatePlayerTurn(doubles, Executors.newCachedThreadPool());
        Assert.assertEquals(gameManager.getPlayers().get(0), new Player("Alex", 3, MAX_DRINKS));
    }

    @Test
    public void testMakePlayerDrink () throws InterruptedException {
        List<Player> players = new CopyOnWriteArrayList<>();

        int maxDrinks = 4;
        Player alex = new Player("Alex", 300, maxDrinks);
        Player bob = new Player("Bob", 400, maxDrinks);
        Player chris = new Player("Chris", 500, maxDrinks);
        players.add(alex);
        players.add(bob);
        players.add(chris);
        GameManager gameManager = new GameManager(players, maxDrinks);
        gameManager.makePlayerDrink(alex);
        gameManager.makePlayerDrink(alex);
        gameManager.makePlayerDrink(alex);
        gameManager.makePlayerDrink(alex);
        gameManager.makePlayerDrink(bob);
        gameManager.makePlayerDrink(bob);
        gameManager.makePlayerDrink(chris);
        gameManager.makePlayerDrink(chris);
        validatePlayerDrinkingState(alex, 4, 0, maxDrinks);
        validatePlayerDrinkingState(bob, 2, 0, maxDrinks);
        validatePlayerDrinkingState(bob, 2, 0, maxDrinks);
        Thread.sleep(2000);
        System.out.println(alex.getNoOfDrinksDrinking() + ": finished : " + alex.getNoOfDrinksFinished());
        validatePlayerDrinkingState(alex, 0, 4, maxDrinks);
    }


    @Test
    public void testMakePlayerDrink_MoreDrinksThanMax() throws InterruptedException {

        List<Player> players = new CopyOnWriteArrayList<>();
        int maxDrinks = 1;
        Player alex = new Player("Alex", 300, maxDrinks);
        Player bob = new Player("Bob", 400, maxDrinks);
        Player chris = new Player("Chris", 500, maxDrinks);
        players.add(alex);
        players.add(bob);
        players.add(chris);
        GameManager gameManager = new GameManager(players, maxDrinks);
        gameManager.makePlayerDrink(alex);
        gameManager.makePlayerDrink(alex);
        gameManager.makePlayerDrink(alex);
        gameManager.makePlayerDrink(alex);
        Thread.sleep(5000);
    }


    private void validatePlayerDrinkingState(Player player, int expectedNoOfDrinksDrinking, int exectedNoOfDrinksFinished, int maxDrinksAllowed) {
        Assert.assertEquals(expectedNoOfDrinksDrinking, player.getNoOfDrinksDrinking());
        Assert.assertEquals(exectedNoOfDrinksFinished, player.getNoOfDrinksFinished());
    }


    @Test
    public void testSimulatePlayerTurn_LoosingThrow() {
        GameManager gameManager = initGameManagerWithMostlySoberPlayer();
        DiceRollOutput doubles = new DiceRollOutput(2,3);
        gameManager.simulatePlayerTurn(doubles, Executors.newCachedThreadPool() );
        Assert.assertEquals(gameManager.getPlayers().get(0), new Player("Bob", 4, MAX_DRINKS));
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
        players.add(new Player("Alex", 3, MAX_DRINKS));
        players.add(new Player("Bob", 4, MAX_DRINKS));
        players.add(new Player("Chris", 5, MAX_DRINKS));
        players.add(new Player("Deepak", 6, MAX_DRINKS));
        players.add(new Player("Eye", 3, MAX_DRINKS));
        players.add(new Player("Federer", 5, MAX_DRINKS));
        players.add(new Player("Garry", 5, MAX_DRINKS));
        players.add(new Player("Harry", 5, MAX_DRINKS));
        players.add(new Player("Ian", 5, MAX_DRINKS));
        players.add(new Player("Jack", 5, MAX_DRINKS));
        players.add(new Player("Kara", 5, MAX_DRINKS));
        return new GameManager(players, MAX_DRINKS);
    }

    private GameManager initGameManagerSomeDrinkingPlayers() {
        List<Player> players = new CopyOnWriteArrayList<>();
        players.add(createDrinkingPlayer("Alex", 3));
        players.add(new Player("Bob", 4, MAX_DRINKS));
        players.add(new Player("Chris", 5, MAX_DRINKS));
        players.add(new Player("Deepak", 6, MAX_DRINKS));
        players.add(new Player("Eye", 3, MAX_DRINKS));
        players.add(createDrinkingPlayer("Federer", 5));
        players.add(new Player("Garry", 5, MAX_DRINKS));
        players.add(new Player("Harry", 5, MAX_DRINKS));
        players.add(new Player("Ian", 5, MAX_DRINKS));
        players.add(createDrinkingPlayer("Jack", 5));
        players.add(new Player("Kara", 5, MAX_DRINKS));
        return new GameManager(players, MAX_DRINKS);
    }


    private Player createDrinkingPlayer(String name, int speedOfDrinking) {
        Player player = new Player(name, speedOfDrinking, MAX_DRINKS);
        player.startDrinking();
        return player;
    }

}
