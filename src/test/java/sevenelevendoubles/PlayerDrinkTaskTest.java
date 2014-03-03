package sevenelevendoubles;

import junit.framework.Assert;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: deepak
 * Date: 3/2/14
 * Time: 6:12 PM
 */
public class PlayerDrinkTaskTest {

    @Test
    public void testTaskSinglePlayerNoThreads() throws Exception {
        Player alex = new Player(100);
        PlayerDrinkTask playerDrinkTask = new PlayerDrinkTask(alex);
        playerDrinkTask.run();
        Assert.assertEquals(alex.getNoOfDrinksDrinking(), 0);
        Assert.assertEquals(alex.getNoOfDrinksFinished(), 1);
        playerDrinkTask.run();
        Assert.assertEquals(alex.getNoOfDrinksDrinking(), 0);
        Assert.assertEquals(alex.getNoOfDrinksFinished(), 2);
    }

    @Test
    public void testTaskTwoPlayers() throws Exception {
        Player alex = new Player(100);
        Player bob = new Player(200);
        PlayerDrinkTask alexDrink = new PlayerDrinkTask(alex);
        PlayerDrinkTask bobDrink = new PlayerDrinkTask(bob);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(alexDrink);
        executorService.submit(bobDrink);
        Thread.sleep(30);
        Assert.assertEquals(1, alex.getNoOfDrinksDrinking());
        Assert.assertEquals(1, bob.getNoOfDrinksDrinking());
        Thread.sleep(70);
        Assert.assertEquals(0, alex.getNoOfDrinksDrinking());
        Assert.assertEquals(1, bob.getNoOfDrinksDrinking());
        Thread.sleep(200);
        Assert.assertEquals(1, alex.getNoOfDrinksFinished());
        Assert.assertEquals(1, bob.getNoOfDrinksFinished());
        executorService.submit(alexDrink);
        executorService.submit(bobDrink);
        Thread.sleep(30);
        Assert.assertEquals(1, alex.getNoOfDrinksDrinking());
        Assert.assertEquals(1, bob.getNoOfDrinksDrinking());
        Assert.assertEquals(1, alex.getNoOfDrinksFinished());
        Assert.assertEquals(1, bob.getNoOfDrinksFinished());
        Thread.sleep(100);
        Assert.assertEquals(0, alex.getNoOfDrinksDrinking());
        Assert.assertEquals(1, bob.getNoOfDrinksDrinking());
        Assert.assertEquals(2, alex.getNoOfDrinksFinished());
        Assert.assertEquals(1, bob.getNoOfDrinksFinished());
        Thread.sleep(100);
        Assert.assertEquals(0, alex.getNoOfDrinksDrinking());
        Assert.assertEquals(0, bob.getNoOfDrinksDrinking());
        Assert.assertEquals(2, alex.getNoOfDrinksFinished());
        Assert.assertEquals(2, bob.getNoOfDrinksFinished());
        executorService.submit(alexDrink);
        executorService.submit(alexDrink);
        executorService.submit(alexDrink);
        Thread.sleep(10);
        Assert.assertEquals(3, alex.getNoOfDrinksDrinking());
        Assert.assertEquals(2, alex.getNoOfDrinksFinished());
        Thread.sleep(100);
        Assert.assertEquals(0, alex.getNoOfDrinksDrinking());
        Assert.assertEquals(5, alex.getNoOfDrinksFinished());
    }

    @Test
    public void testTaskTenPlayers() throws Exception {

    }


}
