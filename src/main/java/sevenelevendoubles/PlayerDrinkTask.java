package sevenelevendoubles;

/**
 * Created with IntelliJ IDEA.
 * User: deepak
 * Date: 3/2/14
 * Time: 5:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerDrinkTask implements Runnable {

    private Player player;

    public PlayerDrinkTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        player.startDrinking();
        try {
            Thread.sleep(player.getSpeedOfDrinking());
            player.endDrinking();
        } catch (InterruptedException e) {
           System.out.println("Thread was interrupted");
        }
    }
}
