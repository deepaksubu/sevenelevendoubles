package sevenelevendoubles.core;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.bean.Player;
import sevenelevendoubles.input.DefaultCommandExecutor;
import sevenelevendoubles.input.DummyCommandExecutor;
import sevenelevendoubles.selector.DeterministicSelector;
import sevenelevendoubles.selector.RandomizedSelector;

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
        GameManager gameManager = new GameManager(players);
        Assert.assertEquals(gameManager.getPlayers().get(0), new Player("Alex", 3));
        Assert.assertEquals(gameManager.getPlayers(), players);
    }

    private List<Player> createInitialPlayerList() {
        List<Player> players = new ArrayList<>();
        Player.maxDrinks = 1;
        players.add(new Player("Alex", 3));
        players.add(new Player("Bob", 4));
        players.add(new Player("Chris", 5));
        return players;
    }

    @Test
    public void testStartSimulation() throws Exception {
        for (int i = 0; i < 10000; i++) {
            Simulator simulator = new Simulator(new DeterministicSelector(), new DummyCommandExecutor());
            simulator.setInitialPlayersList(createInitialPlayerList());
            simulator.setRollSpeed(1);
            Player player = simulator.startSimulation();
            Assert.assertEquals("Alex", player.getName());
        }
    }

}
