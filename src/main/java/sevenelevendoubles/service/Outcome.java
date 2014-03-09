package sevenelevendoubles.service;

/**
 * Outcome of the dice throw. It has two fields
 * 1) a detailed message about the throw
 * 2) a boolean indicating if is a winning throw or a loosing throw
 *
 * User: deepak
 * Date: 3/8/14
 */
public class Outcome {

    private String message;
    private boolean result;

    public Outcome(int d1, int d2) {
        int sum = d1 + d2;
        assert d1 + d2 <= 12: "Sum of dices should always be less than 12";
        if (d1 == d2) {
            this.message = new StringBuilder("double ").append(d1).append("'s").toString();
            this.result = true;
        } else {
            if ( (sum == 11) || (sum == 7)) {
                this.result = true;
            } else {
                this.result = false;
            }
            this.message = Integer.toString(sum);
        }
    }
    public String getMessage() {
        return message;
    }

    public boolean isAWin() {
        return result;
    }
}
