package sevenelevendoubles;

/**
 * Created with IntelliJ IDEA.
 * User: deepak
 * Date: 3/2/14
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {

    String name;
    private int noOfDrinksFinished;
    private int noOfDrinksDrinking;
    private int speedOfDrinking = 100;

    public Player (int speedOfDrinking) {
        this.speedOfDrinking = speedOfDrinking;
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
}
