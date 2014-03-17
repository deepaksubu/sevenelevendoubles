package sevenelevendoubles.input;

import sevenelevendoubles.core.GameMessages;
import sevenelevendoubles.bean.Player;

import java.util.List;

public class DefaultCommandExecutor implements CommandExecutor {

    public DefaultCommandExecutor() {
        System.out.println(GameMessages.HELP_STRING);
    }

    @Override
    public boolean executeStartCommand(List<Player> players) {
        if (players.size() < 2) {
            System.out.println(GameMessages.MORE_PLAYERS_NEEDED);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int executeMaxDrinksCommand(String arg) {
        int intArg = parseIntegerArgument(arg);
        if (intArg > 0) {
            System.out.println(GameMessages.MAX_DRINK_MESSAGE + intArg);
        } else {
            System.out.println(GameMessages.INVALID_NUMBER_MESSAGE);
        }
        return intArg;
    }

    @Override
    public int executeSpeedCommand(String arg) {
        int speed = parseIntegerArgument(arg);
        if (speed > 0) {
            System.out.println(GameMessages.PAUSE_TIME + speed + " seconds");
        } else {
            System.out.println(GameMessages.INVALID_NUMBER_MESSAGE);
        }
        return speed * 1000;
    }

    @Override
    public Player executeAddCommand(String[] args, List<Player> existingPlayers) {
        Player player = null;
        if (args.length < 3) {
            System.out.println(GameMessages.INVALID_ARGUMENTS);
        }
        if (args[1] != null && !args[1].trim().isEmpty()) {
            String name = args[1].trim();
            int speedOfDrinking = parseIntegerArgument(args[2]);
            if (speedOfDrinking > 0) {
                if (!existingPlayers.contains(player)) {
                    player = new Player(name, speedOfDrinking * 1000);
                } else {
                    System.out.println(GameMessages.NAME_ALREADY_TAKEN);
                }
            } else {
                System.out.println(GameMessages.INVALID_NUMBER_MESSAGE);
            }
        } else {
            System.out.println(GameMessages.INVALID_NAME);
        }
        return player;
    }

    int parseIntegerArgument(String argument) {
        try {
            int i = Integer.parseInt(argument.trim());
            return i;
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }
}