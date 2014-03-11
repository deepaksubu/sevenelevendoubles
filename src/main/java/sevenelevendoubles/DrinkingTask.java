package sevenelevendoubles;

import sevenelevendoubles.enums.GameManager;

/**
 * Keep track of a given player's state while drinking a single drink
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
        player.startDrinking();
        try {
            Thread.sleep(player.getSpeedOfDrinkingInMillis());
            player.endDrinking();
            if (player.getNoOfDrinksFinished() >= gameManager.getMaxDrinks()) {
                System.out.println(new StringBuffer(player.getName()).append(" says: 'I've had too many.  I need to stop.'").toString());
                gameManager.removePlayer(player);
            }
        } catch (InterruptedException e) {
            System.out.println("Player was interrupted while drinking");
        }
    }
}
