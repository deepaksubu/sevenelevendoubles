package sevenelevendoubles.entity;

import sevenelevendoubles.core.DrinkingTask;
import sevenelevendoubles.core.GameManager;

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
public class Player {

    String name;

    private int noOfDrinksFinished;
    private int noOfDrinksDrinking;
    private long speedOfDrinkingInMillis;
    private boolean active;

    /**
     * Make sure that the player name is never empty and the speedOfDrinkingInMillis is always positive.
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

    public synchronized void startDrinking(int maxDrinks) {
        if (noOfDrinksFinished + noOfDrinksDrinking < maxDrinks) {
            noOfDrinksDrinking ++;
        }

        if (noOfDrinksFinished + noOfDrinksDrinking > maxDrinks) {
            throw new IllegalStateException(new StringBuffer(name).append(": No of drinks should never be more than").append(maxDrinks).toString());
        }
    }

    public synchronized void endDrinking(int maxDrinks) {
        if (noOfDrinksDrinking < 1) {
            throw new IllegalStateException(new StringBuffer(name).append(": No of drinks drinking should never be negative. Player should be drinking at least one drink to end drinking").toString());
        }

        if (noOfDrinksFinished < maxDrinks) {
            noOfDrinksDrinking--;
            noOfDrinksFinished++;
        }

        if (noOfDrinksFinished > maxDrinks) {
            throw new IllegalStateException(new StringBuffer(name).append(": No of drinks finished should never be more than").append(maxDrinks).toString());
        }
    }
    public long getSpeedOfDrinkingInMillis() {
        return speedOfDrinkingInMillis;
    }

    public synchronized int getTotalDrinks() {
        return (noOfDrinksDrinking + noOfDrinksFinished);
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

}
