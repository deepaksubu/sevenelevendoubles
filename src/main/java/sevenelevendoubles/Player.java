package sevenelevendoubles;

import sevenelevendoubles.enums.Simulator;

import java.util.InputMismatchException;

/**
 * User: deepak
 * Date: 3/2/14
 */
public class Player {

    String name;
    private int noOfDrinksFinished;
    private int noOfDrinksDrinking;
    private int speedOfDrinking = 100;


    /**
     * Make sure that the player name is never empty and the speedOfDrinking is always positive
     * @param name
     * @param speedOfDrinking
     * @return
     */
    public static Player createPlayer(String name, int speedOfDrinking) {
        Player player = new Player();
        if (name.isEmpty()) {
            return null;
        } else {
            player.name = name;
        }

        if (speedOfDrinking <= 0) {
            return null;
        } else {
            player.speedOfDrinking = speedOfDrinking;
        }
        return player;
    }

    public synchronized void startDrinking() {
        noOfDrinksDrinking ++;
    }
    public synchronized void endDrinking() {
        noOfDrinksDrinking--;
        noOfDrinksFinished++;
    }

    public synchronized int getSpeedOfDrinking() {
        return speedOfDrinking;
    }

    public synchronized int getNoOfDrinksFinished() {
        return noOfDrinksFinished;
    }

    public synchronized int getNoOfDrinksDrinking() {
        return noOfDrinksDrinking;
    }

    public String toJoinedGameString() {
        return new StringBuilder(name).append(" ,").append("who can finish a drink in ").append(speedOfDrinking).append(" seconds, has joined the game").toString();
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
