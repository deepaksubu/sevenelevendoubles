package sevenelevendoubles.core;

import sevenelevendoubles.entity.Player;

/**
 * The class indicates the result of a single turn.
 * It consists of the player who finished the last turn and also if the last turn resulted in game completion in which case the finish flag is true
 *
 * User: deepak
 * Date: 3/12/14
 */
public class Result {

    public boolean isGameFinished() {
        return finish;
    }

    public Player getPlayer() {
        return player;
    }

    private boolean finish;
    private Player player;

    public Result(Player player, boolean finish) {
        this.finish = finish;
        this.player = player;
    }
}
