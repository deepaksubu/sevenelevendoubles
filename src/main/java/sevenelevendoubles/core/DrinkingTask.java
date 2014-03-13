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
            synchronized (player) {
                int maxDrinks = gameManager.getMaxDrinks();
                player.startDrinking(maxDrinks);
                Thread.sleep(player.getSpeedOfDrinkingInMillis());
                player.endDrinking(maxDrinks);
                if (player.getTotalDrinks() == maxDrinks) {
                    if (gameManager.getPlayers().size() > 2) {
                        System.out.println(new StringBuffer(player.getName()).append(" says: 'I've had too many.  I need to stop.'").toString());
                    } else if (gameManager.getPlayers().size() == 2) {
                        System.out.println("Waiting for " + player.getName() +" to finish");
                    }
                    gameManager.getPlayers().remove(player);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Player was interrupted while drinking");
        }
    }

}
