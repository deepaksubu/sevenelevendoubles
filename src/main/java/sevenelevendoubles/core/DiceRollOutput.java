package sevenelevendoubles.core;

/**
 * The output of the dice roll. It has two fields
 * 1) a message which should be output for the different types of dice rolls.
 * 2) a boolean indicating if is a winning roll or a loosing roll
 *
 * User: deepak
 * Date: 3/8/14
 */
public class DiceRollOutput {

    private String message;
    private boolean result;

    public DiceRollOutput(int d1, int d2) {
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
