package sevenelevendoubles.core;

import sevenelevendoubles.entity.Player;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    final ExecutorService executorService = Executors.newCachedThreadPool();
    final Selector selector;

    public void setRollSpeed(int rollSpeed) {
        this.rollSpeed = rollSpeed;
    }

    public void setMaxDrinks(int maxDrinks) {
        this.maxDrinks = maxDrinks;
    }

    private int rollSpeed = 2000;

    private int maxDrinks = 5;

    public Simulator(Selector selector, CommandExecutor commandExecutor) {
        this.selector = selector;
        this.commandExecutor = commandExecutor;
        System.out.println(GameMessages.HELP_STRING);
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
            this.maxDrinks = commandExecutor.executeMaxDrinksCommand(args[1]);
        } else if (command.equalsIgnoreCase(CommandExecutor.START_COMMAND)) {
            start = commandExecutor.executeStartCommand(initialPlayersList);
        } else {
            System.out.println(GameMessages.HELP_STRING);
        }
        return start;
    }

    public void startSimulation() {
        GameManager gameManager = createGameManager();
        while (gameManager.simulatePlayerTurn(new DiceThrowResult(selector.selectDiceRoll(), selector.selectDiceRoll()), executorService)) {
            try {
                Thread.sleep(rollSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public GameManager createGameManager() {
        GameManager gameManager = new GameManager(initialPlayersList, maxDrinks);
        return gameManager;
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator(new RandomizedSelector(), new DefaultCommandExecutor());
        simulator.waitForUserInput();
    }

    public void setInitialPlayersList(List<Player> initialPlayersList) {
        this.initialPlayersList = initialPlayersList;
    }
}
