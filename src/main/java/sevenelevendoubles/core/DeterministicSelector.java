package sevenelevendoubles.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

    private static Iterator<Integer> rollIterator = rolls.iterator();

    int i = 0;

    @Override
    public int selectPlayer(int N) {
        return N;
    }

    @Override
    public int selectDiceRoll() {
        if (rollIterator.hasNext()) {
            return rollIterator.next();
        } else {
            rollIterator = rolls.iterator();
            System.out.println("Restarting the iterator");
            return rollIterator.next();
        }
    }
}
