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
        int maxDrinks = 1;
        players.add(new Player("Alex", 3, maxDrinks));
        players.add(new Player("Bob", 4, maxDrinks));
        players.add(new Player("Chris", 5, maxDrinks));
        return players;
    }

    @Test
    public void testStartSimulation() throws Exception {
        for (int i = 0; i < 100; i++) {
            Simulator simulator = new Simulator(new DeterministicSelector(), new DummyCommandExecutor());
            simulator.setInitialPlayersList(createInitialPlayerList());
            simulator.setRollSpeed(1);
            Player player = simulator.startSimulation();
            Assert.assertEquals("Alex", player.getName());
        }
    }

}
