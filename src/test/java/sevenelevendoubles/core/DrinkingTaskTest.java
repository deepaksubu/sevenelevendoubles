package sevenelevendoubles.core;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: deepak
 * Date: 3/2/14
 * Time: 6:12 PM
 */
public class DrinkingTaskTest {

    @Test
    public void testTaskSinglePlayerNoThreads() throws Exception {
        Player alex = Player.createPlayer("name", 100);
        DrinkingTask drinkingTask = new DrinkingTask(alex, createGameManager(5));
        drinkingTask.run();
        validatePlayerDrinkingState(alex, 0, 1, 100);
        drinkingTask.run();
        validatePlayerDrinkingState(alex, 0, 2, 100);
    }

    @Test
    public void testTaskTwoPlayers() throws Exception {
        GameManager gameManager = createGameManager(5);
        Player alex =gameManager.getPlayers().get(0);
        Player bob = gameManager.getPlayers().get(1);
        DrinkingTask alexDrink = new DrinkingTask(alex, gameManager);
        DrinkingTask bobDrink = new DrinkingTask(bob, gameManager);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(alexDrink);
        executorService.submit(bobDrink);
        Thread.sleep(30);
        validatePlayerDrinkingState(alex, 1, 0, 5);
        validatePlayerDrinkingState(bob, 1, 0, 5);
        Thread.sleep(70);
        validatePlayerDrinkingState(alex, 0, 1, 5);
        validatePlayerDrinkingState(bob, 1, 0, 5);
        Thread.sleep(200);
        validatePlayerDrinkingState(alex, 0, 1, 5);
        validatePlayerDrinkingState(bob, 0, 1, 5);
        executorService.submit(alexDrink);
        executorService.submit(bobDrink);
        Thread.sleep(30);
        validatePlayerDrinkingState(alex, 1, 1, 5);
        validatePlayerDrinkingState(bob, 1, 1, 5);
        Thread.sleep(100);
        validatePlayerDrinkingState(alex, 0, 2, 5);
        validatePlayerDrinkingState(bob, 1, 1, 5);
        Thread.sleep(100);
        validatePlayerDrinkingState(alex, 0, 2, 5);
        validatePlayerDrinkingState(bob, 0, 2, 5);
        executorService.submit(alexDrink);
        executorService.submit(alexDrink);
        executorService.submit(alexDrink);
        Thread.sleep(10);
        validatePlayerDrinkingState(alex, 3, 2, 5);
        Thread.sleep(100);
        validatePlayerDrinkingState(alex, 0, 5, 5);
        Assert.assertEquals(4, gameManager.getPlayers().size());
    }

    @Test
    public void testTaskFourPlayers_removeDrunkPlayers() throws Exception {
        GameManager gameManager = createGameManager(1);
        DrinkingTask alexDrink = new DrinkingTask(gameManager.getPlayers().get(0), gameManager);
        DrinkingTask bobDrink = new DrinkingTask(gameManager.getPlayers().get(1), gameManager);
        DrinkingTask chrisDrink = new DrinkingTask(gameManager.getPlayers().get(2), gameManager);
        DrinkingTask damienDrink = new DrinkingTask(gameManager.getPlayers().get(3), gameManager);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(alexDrink);
        executorService.submit(bobDrink);
        executorService.submit(chrisDrink);
        executorService.submit(damienDrink);
        Thread.sleep(40);
        Assert.assertEquals(5, gameManager.getPlayers().size());
        Thread.sleep(100);
        Assert.assertEquals(4, gameManager.getPlayers().size());
        Thread.sleep(230);
        Assert.assertEquals(1, gameManager.getPlayers().size());
    }

    @Test
    public void testTask_InvalidPlayerState() {
        GameManager gameManager = createGameManager(1);
        DrinkingTask alexDrink = new DrinkingTask(gameManager.getPlayers().get(0), gameManager);
        DrinkingTask bobDrink = new DrinkingTask(gameManager.getPlayers().get(1), gameManager);
        DrinkingTask chrisDrink = new DrinkingTask(gameManager.getPlayers().get(2), gameManager);
        DrinkingTask damienDrink = new DrinkingTask(gameManager.getPlayers().get(3), gameManager);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(alexDrink);
        executorService.submit(bobDrink);
        executorService.submit(chrisDrink);
        executorService.submit(damienDrink);
    }


    private void validatePlayerDrinkingState(Player player, int expectedNoOfDrinksDrinking, int exectedNoOfDrinksFinished, int maxDrinksAllowed) {
        Assert.assertEquals(expectedNoOfDrinksDrinking, player.getNoOfDrinksDrinking());
        Assert.assertEquals(exectedNoOfDrinksFinished, player.getNoOfDrinksFinished());
        Assert.assertTrue(player.getNoOfDrinksDrinking() + player.getNoOfDrinksFinished() <= maxDrinksAllowed);
    }

    private GameManager createGameManager(int maxDrinks) {
        List<Player> players = new ArrayList<>();
        players.add(Player.createPlayer("Alex", 100));
        players.add(Player.createPlayer("Bob", 200));
        players.add(Player.createPlayer("Chris", 200));
        players.add(Player.createPlayer("Damien", 200));
        players.add(Player.createPlayer("Erik", 200));
        return new GameManager(players, maxDrinks);
    }

}
