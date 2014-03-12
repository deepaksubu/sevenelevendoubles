package sevenelevendoubles.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: deepak
 * Date: 3/12/14
 */
public class DeterministicSelector implements Selector {

    private final static ArrayList<Integer> rolls = new ArrayList<>();
    static {
        rolls.add(4);
        rolls.add(3);

        rolls.add(4);
        rolls.add(3);

        rolls.add(4);
        rolls.add(3);

        rolls.add(4);
        rolls.add(3);
    }

    @Override
    public int selectPlayer(int N) {
        return N-1;
    }

    @Override
    public int selectDiceRoll() {
        if (rolls.iterator().hasNext()) {
            return rolls.iterator().next();
        } else {
            throw new IndexOutOfBoundsException("Please add more rolls");
        }
    }
}
