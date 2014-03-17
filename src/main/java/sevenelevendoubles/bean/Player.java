package sevenelevendoubles.bean;

import sevenelevendoubles.core.PlayerRemover;

import java.util.concurrent.*;

/**
 * The central bean for the seven eleven game.
 * Each player should always be instantiated with a name and speedOfDrinking and the maxDrinks allowed
 * A player is identified by his or her name.
 *
 * There are two state variables: noOfDrinksFinished and noOfDrinksDrinking which are self explanatory.
 * Once the noOfDrinksFinished becomes equal to the maxDrinks, no further drinking is allowed and the remove method of the PlayerRemover interface is called.
 *
 * User: deepak
 * Date: 3/2/14
 */
public class Player implements Runnable {

    private final static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    private static int maxDrinks = 5;

    private int noOfDrinksFinished;
    private int noOfDrinksDrinking;

    private long speedOfDrinkingInMillis;
    private String name;
    private volatile boolean cancel = false;
    private PlayerRemover playerRemover;

    public int getNoOfDrinksFinished() {
        return noOfDrinksFinished;
    }

    public static void setMaxDrinks(int maxDrinks) {
        Player.maxDrinks = maxDrinks;
    }

    public void setPlayerRemover(PlayerRemover playerRemover) {
        this.playerRemover = playerRemover;
    }

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }


    /**
     * Make sure that the player name is never empty and the speedOfDrinkingInMillis is always positive.
     *
     *
     *
     *
     *
     * @param name
     * @param speedOfDrinking
     * @return
     */
    public Player(String name, long speedOfDrinking) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Player name should never be empty");
        } else {
            this.name = name;
        }

        if (speedOfDrinking <= 0) {
            throw new IllegalArgumentException("Speed of drinking should never be negative");
        } else {
            this.speedOfDrinkingInMillis = speedOfDrinking;
        }
    }

    public synchronized void printStatus() {
        StringBuffer stringBuffer = new StringBuffer(name).append(" has had ").append(noOfDrinksFinished).append(" drinks");
        if (noOfDrinksDrinking > 0) {
            stringBuffer.append(" and is drinking ").append(noOfDrinksDrinking).append(" more");
        }
        System.out.println(stringBuffer.toString());
    }

    // Increment the drinking count for a given player. This will only be applicable if the noOfDrinksFinished is less than the maxAllowedDrinks
    public synchronized void startDrinking() {
        if (noOfDrinksFinished + noOfDrinksDrinking < maxDrinks) {
            noOfDrinksDrinking ++;

            if (noOfDrinksDrinking == 1) {
                scheduledExecutorService.schedule(this, speedOfDrinkingInMillis, TimeUnit.MILLISECONDS);
            }
        }
    }

    // This function will recursively schedule itself after the delay Player#speedOfDrinkingInMillis
    public synchronized void endDrinking() {
        if (noOfDrinksDrinking > 0) {
            if (noOfDrinksFinished < maxDrinks) {
                noOfDrinksDrinking--;
                noOfDrinksFinished++;
                if (noOfDrinksDrinking > 0) {
                    scheduledExecutorService.schedule(this, speedOfDrinkingInMillis, TimeUnit.MILLISECONDS);
                }

                if (noOfDrinksDrinking == 0) {
                    if (noOfDrinksFinished == maxDrinks) {
                        playerRemover.removeFromPlayerList(this);
                        System.out.println(new StringBuffer(name).append(" says: 'I've had too many.  I need to stop.'").toString());
                        cancel = true;
                    }
                }
            }
        }
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized int getNoOfDrinksDrinking() {
        return noOfDrinksDrinking;
    }

    public String toJoinedGameString() {
        return new StringBuilder(name).append(" ,").append("who can finish a drink in ").append(speedOfDrinkingInMillis/1000).append(" seconds, has joined the game").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (!name.equals(player.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public void run() {
        if (!cancel) {
            endDrinking();
        }
    }


}
