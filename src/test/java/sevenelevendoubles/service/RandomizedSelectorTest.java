package sevenelevendoubles.service;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.enums.RandomizedSelector;
import sevenelevendoubles.enums.Selector;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class RandomizedSelectorTest {

    Selector selector = new RandomizedSelector();

    @Test
    public void testRoll() throws Exception {
        for (int i=0; i < 100; i++) {
            int roll = selector.selectDiceRoll();
            System.out.println("Rolled a " + roll);
            Assert.assertTrue(roll <= 6);
            Assert.assertTrue(roll > 0);
            int player = selector.selectPlayer(5);
            Assert.assertTrue(player <= 5);
            Assert.assertTrue(player > 0);
        }
    }
}
