package sevenelevendoubles.core;

import sevenelevendoubles.entity.Player;

/**
 * The task of drinking a single drink with a consequence that if the no of drinks are greater than the
 * User: deepak
 */
public class DrinkingTask implements Runnable {

    private Player player;
    private GameManager gameManager;
    private Object lock = new Object();

    public DrinkingTask(Player player, GameManager gameManager) {
        this.player = player;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        GameManager.scheduledExecutorService.submit(player);
    }

}
