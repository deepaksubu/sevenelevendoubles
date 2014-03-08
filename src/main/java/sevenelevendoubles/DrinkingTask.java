package sevenelevendoubles;

/**
 * Keep track of a given player's state while drinking a single drink
 * User: deepak
 */
public class DrinkingTask implements Runnable {

    private Player player;

    public DrinkingTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        player.startDrinking();
        try {
            Thread.sleep(player.getSpeedOfDrinking());
            player.endDrinking();
        } catch (InterruptedException e) {
           System.out.println("Player was interrupted while drinking");
        }
    }
}
