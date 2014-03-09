package sevenelevendoubles.service;

import junit.framework.Assert;
import org.junit.Test;

import static sevenelevendoubles.service.Dice.roll;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class DiceTest {

    @Test
    public void testRoll() throws Exception {
        for (int i=0; i < 100; i++) {
            int roll = roll();
            System.out.println("Rolled a " + roll);
            Assert.assertTrue(roll <= 6);
            Assert.assertTrue(roll > 0);
        }
    }
}
