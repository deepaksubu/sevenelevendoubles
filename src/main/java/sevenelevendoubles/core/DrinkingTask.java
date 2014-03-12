package sevenelevendoubles.core;

import sevenelevendoubles.entity.Player;

/**
 * The task of drinking a single drink with a consequence that if the no of drinks are greater than the
 * User: deepak
 */
public class DrinkingTask implements Runnable {

    private Player player;
    private GameManager gameManager;

    public DrinkingTask(Player player, GameManager gameManager) {
        this.player = player;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        try {
            player.startDrinking();
            Thread.sleep(player.getSpeedOfDrinkingInMillis());
            player.endDrinking();
            if (player.getTotalDrinks() == gameManager.getMaxDrinks()) {
                System.out.println(new StringBuffer(player.getName()).append(" says: 'I've had too many.  I need to stop.'").toString());
                gameManager.removePlayer(player);
            }
        } catch (InterruptedException e) {
            System.out.println("Player was interrupted while drinking");
        }
    }
}
