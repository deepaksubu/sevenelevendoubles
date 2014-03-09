package sevenelevendoubles.service;

import java.util.Random;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class Dice {

    public static int roll() {
        return (int) (Math.random()*6 + 1);
    }
}
