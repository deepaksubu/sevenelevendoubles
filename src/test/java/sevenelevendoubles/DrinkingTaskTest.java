package sevenelevendoubles;

import junit.framework.Assert;
import org.junit.Test;

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
        DrinkingTask drinkingTask = new DrinkingTask(alex);
        drinkingTask.run();
        validatePlayerDrinkingState(alex, 0, 1);
        drinkingTask.run();
        validatePlayerDrinkingState(alex, 0, 2);
    }

    @Test
    public void testTaskTwoPlayers() throws Exception {
        Player alex = Player.createPlayer("name", 100);
        Player bob = Player.createPlayer("name", 200);
        DrinkingTask alexDrink = new DrinkingTask(alex);
        DrinkingTask bobDrink = new DrinkingTask(bob);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(alexDrink);
        executorService.submit(bobDrink);
        Thread.sleep(30);
        validatePlayerDrinkingState(alex, 1, 0);
        validatePlayerDrinkingState(bob, 1, 0);
        Thread.sleep(70);
        validatePlayerDrinkingState(alex, 0, 1);
        validatePlayerDrinkingState(bob, 1, 0);
        Thread.sleep(200);
        validatePlayerDrinkingState(alex, 0, 1);
        validatePlayerDrinkingState(bob, 0, 1);
        executorService.submit(alexDrink);
        executorService.submit(bobDrink);
        Thread.sleep(30);
        validatePlayerDrinkingState(alex, 1, 1);
        validatePlayerDrinkingState(bob, 1, 1);
        Thread.sleep(100);
        validatePlayerDrinkingState(alex, 0, 2);
        validatePlayerDrinkingState(bob, 1, 1);
        Thread.sleep(100);
        validatePlayerDrinkingState(alex, 0, 2);
        validatePlayerDrinkingState(bob, 0, 2);
        executorService.submit(alexDrink);
        executorService.submit(alexDrink);
        executorService.submit(alexDrink);
        Thread.sleep(10);
        validatePlayerDrinkingState(alex, 3, 2);
        Thread.sleep(100);
        validatePlayerDrinkingState(alex, 0, 5);
    }

    private void validatePlayerDrinkingState(Player player, int expectedNoOfDrinksDrinking, int exectedNoOfDrinksFinished) {
        Assert.assertEquals(expectedNoOfDrinksDrinking, player.getNoOfDrinksDrinking());
        Assert.assertEquals(exectedNoOfDrinksFinished, player.getNoOfDrinksFinished());
    }

}
