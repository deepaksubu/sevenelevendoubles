package sevenelevendoubles.input;

import sevenelevendoubles.bean.Player;

import java.util.List;

/**
 * User: deepak
 * Date: 3/12/14
 */
public class DummyCommandExecutor implements CommandExecutor {

    public DummyCommandExecutor() {
        System.out.println("Start New Round");
    }

    @Override
    public boolean executeStartCommand(List<Player> players) {
        return false;
    }

    @Override
    public int executeMaxDrinksCommand(String arg) {
        return 0;
    }

    @Override
    public int executeSpeedCommand(String arg) {
        return 0;
    }

    @Override
    public Player executeAddCommand(String[] args, List<Player> existingPlayers) {
        return null;
    }
}
