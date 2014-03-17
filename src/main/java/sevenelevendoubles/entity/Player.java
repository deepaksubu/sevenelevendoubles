package sevenelevendoubles.entity;

import sevenelevendoubles.core.PlayerRemover;
import sevenelevendoubles.core.GameManager;

import java.util.concurrent.*;

/**
 * The central entity for the seven eleven game.
 * Each player should always be instantiated with a name and speedOfDrinking.
 * A player is identified by his or her name.
 *
 * At any point of time, the player can be in two states, drinking or not drinking.
 * If a player is in drinking state, then the noOfDrinksDrinking always has to be greater than zero.
 *
 * noOfDrinksFinished and noOfDrinksDrinking which are self explanatory.
 *
 * User: deepak
 * Date: 3/2/14
 */
public class Player implements Callable<Player> {

    private String name;
    private int noOfDrinksFinished;
    private int noOfDrinksDrinking;
    private long speedOfDrinkingInMillis;
    private final int maxDrinks;

    public int getNoOfDrinksFinished() {
        return noOfDrinksFinished;
    }

    public void setPlayerRemover(PlayerRemover playerRemover) {
        this.playerRemover = playerRemover;
    }

    private PlayerRemover playerRemover;

    /**
     * Make sure that the player name is never empty and the speedOfDrinkingInMillis is always positive.
     *
     *
     *
     *
     * @param name
     * @param speedOfDrinking
     * @param maxDrinks
     * @return
     */
    public Player(String name, long speedOfDrinking, int maxDrinks) {
        this.maxDrinks = maxDrinks;
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
        if (noOfDrinksFinished < maxDrinks) {
            noOfDrinksDrinking ++;

            if (noOfDrinksDrinking == 1) {
                GameManager.scheduledExecutorService.schedule(this, speedOfDrinkingInMillis, TimeUnit.MILLISECONDS);
            }
        }
    }

    public synchronized void endDrinking() throws MaximumDrinksExceededException {
        if (noOfDrinksDrinking < 1) {
            throw new IllegalStateException(new StringBuffer(name).append("No of drinks drinking should never be negative").toString());
        }

        if (noOfDrinksFinished >= maxDrinks) {
            throw new MaximumDrinksExceededException(name);
        }

        noOfDrinksDrinking--;
        noOfDrinksFinished++;

        if (noOfDrinksDrinking > 0) {
            ScheduledFuture<Player> schedule = GameManager.scheduledExecutorService.schedule(this, speedOfDrinkingInMillis, TimeUnit.MILLISECONDS);
        }


        if (noOfDrinksFinished == maxDrinks) {
            playerRemover.removeFromPlayerList(this);
            System.out.println(new StringBuffer(name).append(" says: 'I've had too many.  I need to stop.'").toString());
        }

        if (noOfDrinksFinished > maxDrinks) {
            System.out.println("Throwing Exception");
            throw new MaximumDrinksExceededException(name);
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
    public Player call() throws MaximumDrinksExceededException {
        endDrinking();
        return this;
    }


}
