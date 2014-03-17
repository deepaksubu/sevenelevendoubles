package sevenelevendoubles.core;

import sevenelevendoubles.entity.Player;

import java.util.List;

/**
 * User: deepak
 * Date: 3/12/14
 */
public interface CommandExecutor {
    String ADD_COMMAND = "ADD";
    String SPEED_COMMAND = "SPEED";
    String MAX_COMMAND = "MAX";
    String START_COMMAND = "START";
    String HELP_COMMAND = "HELP";

    boolean executeStartCommand(List<Player> players);

    int executeMaxDrinksCommand(String arg);

    int executeSpeedCommand(String arg);

    Player executeAddCommand(String[] args, List<Player> existingPlayers, int maxDrinks);
}
