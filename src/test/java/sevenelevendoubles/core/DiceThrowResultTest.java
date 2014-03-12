package sevenelevendoubles.core;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class DiceThrowResultTest {

    @Test
    public void testOutcomes_Doubles() throws Exception {
        DiceThrowResult diceThrowResult = new DiceThrowResult(1,1);
        Assert.assertEquals(diceThrowResult.isAWin(), true);
        Assert.assertEquals(diceThrowResult.getMessage(), "double 1's");

        diceThrowResult = new DiceThrowResult(2,2);
        Assert.assertEquals(diceThrowResult.isAWin(), true);
        Assert.assertEquals(diceThrowResult.getMessage(), "double 2's");

        diceThrowResult = new DiceThrowResult(3,3);
        Assert.assertEquals(diceThrowResult.isAWin(), true);
        Assert.assertEquals(diceThrowResult.getMessage(), "double 3's");

        diceThrowResult = new DiceThrowResult(4,4);
        Assert.assertEquals(diceThrowResult.isAWin(), true);
        Assert.assertEquals(diceThrowResult.getMessage(), "double 4's");
    }

    @Test
    public void testOutcomes_SevenEleven() throws Exception {
        DiceThrowResult diceThrowResult = new DiceThrowResult(3,4);
        Assert.assertEquals(diceThrowResult.isAWin(), true);
        Assert.assertEquals(diceThrowResult.getMessage(), "7");

        diceThrowResult = new DiceThrowResult(1,6);
        Assert.assertEquals(diceThrowResult.isAWin(), true);
        Assert.assertEquals(diceThrowResult.getMessage(), "7");

        diceThrowResult = new DiceThrowResult(5,6);
        Assert.assertEquals(diceThrowResult.isAWin(), true);
        Assert.assertEquals(diceThrowResult.getMessage(), "11");

        diceThrowResult = new DiceThrowResult(6,1);
        Assert.assertEquals(diceThrowResult.isAWin(), true);
        Assert.assertEquals(diceThrowResult.getMessage(), "7");

        diceThrowResult = new DiceThrowResult(6,5);
        Assert.assertEquals(diceThrowResult.isAWin(), true);
        Assert.assertEquals(diceThrowResult.getMessage(), "11");

        diceThrowResult = new DiceThrowResult(4,3);
        Assert.assertEquals(diceThrowResult.isAWin(), true);
        Assert.assertEquals(diceThrowResult.getMessage(), "7");
    }

    @Test
    public void testOutcomes_LoosingThrows() throws Exception {

        DiceThrowResult diceThrowResult = new DiceThrowResult(4,2);
        Assert.assertEquals(diceThrowResult.isAWin(), false);
        Assert.assertEquals(diceThrowResult.getMessage(), "6");

        diceThrowResult = new DiceThrowResult(2,6);
        Assert.assertEquals(diceThrowResult.isAWin(), false);
        Assert.assertEquals(diceThrowResult.getMessage(), "8");

        diceThrowResult = new DiceThrowResult(6,4);
        Assert.assertEquals(diceThrowResult.isAWin(), false);
        Assert.assertEquals(diceThrowResult.getMessage(), "10");

        diceThrowResult = new DiceThrowResult(6,2);
        Assert.assertEquals(diceThrowResult.isAWin(), false);
        Assert.assertEquals(diceThrowResult.getMessage(), "8");

        diceThrowResult = new DiceThrowResult(5,4);
        Assert.assertEquals(diceThrowResult.isAWin(), false);
        Assert.assertEquals(diceThrowResult.getMessage(), "9");

        diceThrowResult = new DiceThrowResult(4,5);
        Assert.assertEquals(diceThrowResult.isAWin(), false);
        Assert.assertEquals(diceThrowResult.getMessage(), "9");
    }

}
