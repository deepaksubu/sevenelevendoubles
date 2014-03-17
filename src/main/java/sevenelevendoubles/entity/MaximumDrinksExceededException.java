package sevenelevendoubles.entity;

/**
 * User: deepak
 * Date: 3/16/14
 */
public class MaximumDrinksExceededException extends Exception {

    public MaximumDrinksExceededException(String message) {
        super("Maximum limit exceeded for player:" + message);
    }
}
