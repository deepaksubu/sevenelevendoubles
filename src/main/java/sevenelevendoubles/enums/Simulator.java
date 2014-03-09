package sevenelevendoubles.enums;

import sevenelevendoubles.Player;

import java.util.*;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class Simulator {

    public static final String INVALID_NUMBER_MESSAGE = "Please provide a non zero positive number";
    public static final String INVALID_ARGUMENTS = "Incorrect number of arguments for 'ADD";
    public static final String INVALID_NAME = "Please enter a valid non empty string to use for name";
    private static final String NAME_ALREADY_TAKEN = "Enter a different name as the provided one is already taken";


    List<Player> initialPlayersList = new LinkedList<>();
    private int rollSpeed = 2;

    private static final String HELP_STRING = "This program simulates the 7-11-Doubles drinking game.\n" +
            "\n" +
            "The object of the game is to make other players drink their favorite beverage.  Each player takes turns rolling a pair of dice.  A winning roll of the dice occurs if the total of the dice is 7 or 11, or the dice show doubles (1-1, 2-2, etc.)  When the current player rolls a winning combination, they choose some other player to drink their beverage and the current player gets to roll again.  Any other combination is a losing roll and the current player's turn is over and the dice are passed to the next player.  If the current player rolls a losing combination and any other player is has not finished drinking, the current player ignores the losing roll and rolls again. Winning rolls are treated as above.  It is possible for more than one other player to be drinking at the same time.  In this simulation, the game can begin after two players have joined.  The game ends when only one player is left.  A player will leave the game after being made to drink a specified number of drinks.  If the player has drinks remaining, they must finish them, but should not be assigned any more drinks.\n" +
            " \n" +
            "Command:\n" +
            "\tHELP\tPrint these instruction\n" +
            "\tADD [player name] [drinking time]\tAdds named player.  \n" +
            "\t\tDrinking time is time to finish 1 drink\n" +
            "\tSPEED [seconds]\tNumber of seconds between rolls. \n" +
            "\t\tDefault 2.\n" +
            "\tMAX [drinks]\tNumber of drinks before player.\n" +
            "\t\tdrops out. Default 5.\n" +
            "\tSTART\tStart the simulation";

    private static final String PAUSE_TIME = "Pause time between rolls is ";
    private static final String MAX_DRINK_MESSAGE = "Maximum number of drinks is ";
    private static final String MORE_PLAYERS_NEEDED = "At least 2 players required to start game";

    private int max = 5;

    public Simulator() {
        System.out.println(HELP_STRING);
    }

    //TODO: Refactor to remove the nasty if elses
    public void waitForInput() {
        System.out.print("%%% ");
        Scanner scanner = new Scanner(System.in);
        String commandLine = scanner.nextLine();
        String commands[] = commandLine.split(" ");
        String commandString = commands[0].trim();
        if (commandString.equalsIgnoreCase(Command.HELP.toString())) {
            System.out.println(HELP_STRING);
        } else if (commandString.equalsIgnoreCase(Command.ADD.toString())) {
            executeAddCommand(commands);
        } else if (commandString.equalsIgnoreCase(Command.SPEED.toString())) {
            executeSpeedCommand(commands[1]);
        } else if (commandString.equalsIgnoreCase(Command.MAX.toString())) {
            executeMaxDrinksCommand(commands[1]);
        } else if (commandString.equalsIgnoreCase(Command.START.toString())) {
            executeStartCommand();
        } else {
            System.out.println(HELP_STRING);
        }
        waitForInput();
    }

    private void executeStartCommand() {
        if (initialPlayersList.size() < 2) {
            System.out.println(MORE_PLAYERS_NEEDED);
        } else {
            startSimulation();
        }
    }

    private void executeMaxDrinksCommand(String command) {
        int max = parseIntegerArgument(command);
        if (max > 0) {
            this.max = max;
            System.out.println(MAX_DRINK_MESSAGE + this.max);
        } else {
            System.out.println(INVALID_NUMBER_MESSAGE);
        }
    }

    private void executeSpeedCommand(String command) {
        int speed = parseIntegerArgument(command);
        if (speed > 0) {
            this.rollSpeed = speed;
            System.out.println(PAUSE_TIME + this.rollSpeed +" seconds");
        } else {
            System.out.println(INVALID_NUMBER_MESSAGE);
        }
    }

    private void executeAddCommand(String[] commands) {
        if (commands.length < 3) {
            System.out.println(INVALID_ARGUMENTS);
        } else {
            if (commands[1] != null && !commands[1].trim().isEmpty()) {
                String name = commands[1].trim();
                int speedOfDrinking = parseIntegerArgument(commands[2]);
                if (speedOfDrinking > 0) {
                    Player player = Player.createPlayer(name, speedOfDrinking);
                    if (player != null) {
                        if (!initialPlayersList.contains(player)) {
                            initialPlayersList.add(player);
                            System.out.println(player.toJoinedGameString());
                        } else {
                            System.out.println(NAME_ALREADY_TAKEN);
                        }
                    }
                } else {
                    System.out.println(INVALID_NUMBER_MESSAGE);
                }
            } else {
                System.out.println(INVALID_NAME);
            }
        }
    }

    private int parseIntegerArgument(String argument) {
        try {
            int i = Integer.parseInt(argument.trim());
            if (i > 0) {
                return i;
            } else {
                return -1;
            }
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }

    private void startSimulation() {
        System.out.println("Simulation Started");
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.waitForInput();
    }
}
