package sevenelevendoubles;

/**
 * User: deepak
 * Date: 3/2/14
 */
public class Player {

    String name;

    private int noOfDrinksFinished;
    private int noOfDrinksDrinking;
    private long speedOfDrinkingInMillis;
    /**
     * Make sure that the player name is never empty and the speedOfDrinkingInMillis is always positive
     *
     * @param name
     * @param speedOfDrinking
     * @return
     */
    public static Player createPlayer(String name, long speedOfDrinking) {
        Player player = new Player();
        if (name.isEmpty()) {
            return null;
        } else {
            player.name = name;
        }

        if (speedOfDrinking <= 0) {
            return null;
        } else {
            player.speedOfDrinkingInMillis = speedOfDrinking;
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
    public long getSpeedOfDrinkingInMillis() {
        return speedOfDrinkingInMillis;
    }

    public synchronized int getNoOfDrinksFinished() {
        return noOfDrinksFinished;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized int getNoOfDrinksDrinking() {
        return noOfDrinksDrinking;
    }

    public String toJoinedGameString() {
        return new StringBuilder(name).append(" ,").append("who can finish a drink in ").append(speedOfDrinkingInMillis).append(" seconds, has joined the game").toString();
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
