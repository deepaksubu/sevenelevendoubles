package sevenelevendoubles.core;

import sevenelevendoubles.bean.DiceRollOutput;
import sevenelevendoubles.bean.Player;
import sevenelevendoubles.bean.Result;
import sevenelevendoubles.input.CommandExecutor;
import sevenelevendoubles.input.DefaultCommandExecutor;
import sevenelevendoubles.selector.RandomizedSelector;
import sevenelevendoubles.selector.Selector;

import java.util.*;

/**
 * This gets the inputs from the user and does the game simulation.
 * This is a singleton class which talks to the gameManager singleton for doing its activity.
 *
 * User: deepak
 * Date: 3/8/14
 */
public class Simulator {


    private CommandExecutor commandExecutor;
    private List<Player> initialPlayersList = new ArrayList<>();
    private final Selector selector;

    public void setRollSpeed(int rollSpeed) {
        this.rollSpeed = rollSpeed;
    }

    private int rollSpeed = 2000;

    public Simulator(Selector selector, CommandExecutor commandExecutor) {
        this.selector = selector;
        this.commandExecutor = commandExecutor;
    }

    public void waitForUserInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("=>");
            String commandLine = scanner.nextLine();
            String arguments[] = commandLine.split(" ");
            String command = arguments[0].trim();
            boolean startSimulation = doExecute(command, arguments);
            if (startSimulation) {
                startSimulation();
            } else {
                waitForUserInput();
            }
        } finally {
            scanner.close();
        }
        System.exit(0);

    }

    private boolean doExecute(String command, String[] args) {
        boolean start = false;
        if (command.equalsIgnoreCase(CommandExecutor.HELP_COMMAND)) {
            System.out.println(GameMessages.HELP_STRING);
        } else if (command.equalsIgnoreCase(CommandExecutor.ADD_COMMAND)) {
            Player player = commandExecutor.executeAddCommand(args, initialPlayersList);
            if (player != null) {
                initialPlayersList.add(player);
                player.toJoinedGameString();
            }
        } else if (command.equalsIgnoreCase(CommandExecutor.SPEED_COMMAND)) {
            this.rollSpeed = commandExecutor.executeSpeedCommand(args[1]);
        } else if (command.equalsIgnoreCase(CommandExecutor.MAX_COMMAND)) {
            Player.maxDrinks = commandExecutor.executeMaxDrinksCommand(args[1]);
        } else if (command.equalsIgnoreCase(CommandExecutor.START_COMMAND)) {
            start = commandExecutor.executeStartCommand(initialPlayersList);
        } else {
            System.out.println(GameMessages.HELP_STRING);
        }
        return start;
    }

    public Player startSimulation() {
        GameManager gameManager = new GameManager(initialPlayersList);
        Result result = gameManager.simulatePlayerTurn(new DiceRollOutput(selector.selectDiceRoll(), selector.selectDiceRoll()), selector);
        while (!result.isGameFinished()) {
            try {
                Thread.sleep(rollSpeed);
                result = gameManager.simulatePlayerTurn(new DiceRollOutput(selector.selectDiceRoll(), selector.selectDiceRoll()), selector);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result.getPlayer();
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator(new RandomizedSelector(), new DefaultCommandExecutor());
        simulator.waitForUserInput();
    }

    public void setInitialPlayersList(List<Player> initialPlayersList) {
        this.initialPlayersList = initialPlayersList;
    }
}
