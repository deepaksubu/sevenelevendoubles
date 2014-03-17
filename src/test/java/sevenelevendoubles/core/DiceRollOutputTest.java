package sevenelevendoubles.core;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.bean.DiceRollOutput;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class DiceRollOutputTest {

    @Test
    public void testOutcomes_Doubles() throws Exception {
        DiceRollOutput diceRollOutput = new DiceRollOutput(1,1);
        Assert.assertEquals(diceRollOutput.isAWin(), true);
        Assert.assertEquals(diceRollOutput.getMessage(), "double 1's");

        diceRollOutput = new DiceRollOutput(2,2);
        Assert.assertEquals(diceRollOutput.isAWin(), true);
        Assert.assertEquals(diceRollOutput.getMessage(), "double 2's");

        diceRollOutput = new DiceRollOutput(3,3);
        Assert.assertEquals(diceRollOutput.isAWin(), true);
        Assert.assertEquals(diceRollOutput.getMessage(), "double 3's");

        diceRollOutput = new DiceRollOutput(4,4);
        Assert.assertEquals(diceRollOutput.isAWin(), true);
        Assert.assertEquals(diceRollOutput.getMessage(), "double 4's");
    }

    @Test
    public void testOutcomes_SevenEleven() throws Exception {
        DiceRollOutput diceRollOutput = new DiceRollOutput(3,4);
        Assert.assertEquals(diceRollOutput.isAWin(), true);
        Assert.assertEquals(diceRollOutput.getMessage(), "7");

        diceRollOutput = new DiceRollOutput(1,6);
        Assert.assertEquals(diceRollOutput.isAWin(), true);
        Assert.assertEquals(diceRollOutput.getMessage(), "7");

        diceRollOutput = new DiceRollOutput(5,6);
        Assert.assertEquals(diceRollOutput.isAWin(), true);
        Assert.assertEquals(diceRollOutput.getMessage(), "11");

        diceRollOutput = new DiceRollOutput(6,1);
        Assert.assertEquals(diceRollOutput.isAWin(), true);
        Assert.assertEquals(diceRollOutput.getMessage(), "7");

        diceRollOutput = new DiceRollOutput(6,5);
        Assert.assertEquals(diceRollOutput.isAWin(), true);
        Assert.assertEquals(diceRollOutput.getMessage(), "11");

        diceRollOutput = new DiceRollOutput(4,3);
        Assert.assertEquals(diceRollOutput.isAWin(), true);
        Assert.assertEquals(diceRollOutput.getMessage(), "7");
    }

    @Test
    public void testOutcomes_LoosingThrows() throws Exception {

        DiceRollOutput diceRollOutput = new DiceRollOutput(4,2);
        Assert.assertEquals(diceRollOutput.isAWin(), false);
        Assert.assertEquals(diceRollOutput.getMessage(), "6");

        diceRollOutput = new DiceRollOutput(2,6);
        Assert.assertEquals(diceRollOutput.isAWin(), false);
        Assert.assertEquals(diceRollOutput.getMessage(), "8");

        diceRollOutput = new DiceRollOutput(6,4);
        Assert.assertEquals(diceRollOutput.isAWin(), false);
        Assert.assertEquals(diceRollOutput.getMessage(), "10");

        diceRollOutput = new DiceRollOutput(6,2);
        Assert.assertEquals(diceRollOutput.isAWin(), false);
        Assert.assertEquals(diceRollOutput.getMessage(), "8");

        diceRollOutput = new DiceRollOutput(5,4);
        Assert.assertEquals(diceRollOutput.isAWin(), false);
        Assert.assertEquals(diceRollOutput.getMessage(), "9");

        diceRollOutput = new DiceRollOutput(4,5);
        Assert.assertEquals(diceRollOutput.isAWin(), false);
        Assert.assertEquals(diceRollOutput.getMessage(), "9");
    }

}
