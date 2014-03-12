package sevenelevendoubles.core;

/**
* User: deepak
* Date: 3/10/14
*/
public class RandomizedSelector implements Selector {

    /**
     * Returns a random player, given N players
     *
     * @param N
     * @return
     */
    @Override
    public int selectPlayer(int N) {
        int selected = (int) (Math.random() * N);
        return selected + 1;
    }

    @Override
    public int selectDiceRoll() {
        return (int) (Math.random()*6 + 1);
    }
}
