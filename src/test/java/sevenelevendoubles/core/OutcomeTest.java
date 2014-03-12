package sevenelevendoubles.core;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class OutcomeTest {

    @Test
    public void testOutcomes_Doubles() throws Exception {
        Outcome outcome = new Outcome(1,1);
        Assert.assertEquals(outcome.isAWin(), true);
        Assert.assertEquals(outcome.getMessage(), "double 1's");

        outcome = new Outcome(2,2);
        Assert.assertEquals(outcome.isAWin(), true);
        Assert.assertEquals(outcome.getMessage(), "double 2's");

        outcome = new Outcome(3,3);
        Assert.assertEquals(outcome.isAWin(), true);
        Assert.assertEquals(outcome.getMessage(), "double 3's");

        outcome = new Outcome(4,4);
        Assert.assertEquals(outcome.isAWin(), true);
        Assert.assertEquals(outcome.getMessage(), "double 4's");
    }

    @Test
    public void testOutcomes_SevenEleven() throws Exception {
        Outcome outcome = new Outcome(3,4);
        Assert.assertEquals(outcome.isAWin(), true);
        Assert.assertEquals(outcome.getMessage(), "7");

        outcome = new Outcome(1,6);
        Assert.assertEquals(outcome.isAWin(), true);
        Assert.assertEquals(outcome.getMessage(), "7");

        outcome = new Outcome(5,6);
        Assert.assertEquals(outcome.isAWin(), true);
        Assert.assertEquals(outcome.getMessage(), "11");

        outcome = new Outcome(6,1);
        Assert.assertEquals(outcome.isAWin(), true);
        Assert.assertEquals(outcome.getMessage(), "7");

        outcome = new Outcome(6,5);
        Assert.assertEquals(outcome.isAWin(), true);
        Assert.assertEquals(outcome.getMessage(), "11");

        outcome = new Outcome(4,3);
        Assert.assertEquals(outcome.isAWin(), true);
        Assert.assertEquals(outcome.getMessage(), "7");
    }

    @Test
    public void testOutcomes_LoosingThrows() throws Exception {

        Outcome outcome = new Outcome(4,2);
        Assert.assertEquals(outcome.isAWin(), false);
        Assert.assertEquals(outcome.getMessage(), "6");

        outcome = new Outcome(2,6);
        Assert.assertEquals(outcome.isAWin(), false);
        Assert.assertEquals(outcome.getMessage(), "8");

        outcome = new Outcome(6,4);
        Assert.assertEquals(outcome.isAWin(), false);
        Assert.assertEquals(outcome.getMessage(), "10");

        outcome = new Outcome(6,2);
        Assert.assertEquals(outcome.isAWin(), false);
        Assert.assertEquals(outcome.getMessage(), "8");

        outcome = new Outcome(5,4);
        Assert.assertEquals(outcome.isAWin(), false);
        Assert.assertEquals(outcome.getMessage(), "9");

        outcome = new Outcome(4,5);
        Assert.assertEquals(outcome.isAWin(), false);
        Assert.assertEquals(outcome.getMessage(), "9");
    }

}
