package sevenelevendoubles.core;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class SimulatorTest {

    @Test
    public void testInitializeSimulation() throws Exception {
        Simulator simulator = new Simulator(new RandomizedSelector(), new DefaultCommandExecutor());
        List<Player> players = createInitialPlayerList();
        simulator.setInitialPlayersList(players);
        GameManager gameManager = new GameManager(players, 3);
        Assert.assertEquals(gameManager.getPlayers().get(0), new Player("Alex", 3, 3));
        Assert.assertEquals(gameManager.getPlayers(), players);
    }

    private List<Player> createInitialPlayerList() {
        List<Player> players = new ArrayList<>();
        int maxDrinks = 3;
        players.add(new Player("Alex", 30, maxDrinks));
        players.add(new Player("Bob", 40, maxDrinks));
        players.add(new Player("Chris", 50, maxDrinks));
        return players;
    }

    @Test
    public void testStartSimulation() throws Exception {
        for (int i = 0; i < 10; i++) {
            Simulator simulator = new Simulator(new DeterministicSelector(), new DummyCommandExecutor());
            simulator.setInitialPlayersList(createInitialPlayerList());
            simulator.setMaxDrinks(1);
            simulator.setRollSpeed(10);
            Player player = simulator.startSimulation();
            Assert.assertEquals(player.getName(), "Alex");
        }
    }

}
