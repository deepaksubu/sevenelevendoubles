package sevenelevendoubles.enums;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.Player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static sevenelevendoubles.Player.createPlayer;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class SimulatorTest {

    @Test
    public void testInitializeSimulation() throws Exception {
        Simulator simulator = new Simulator();
        Queue<Player> players = new ConcurrentLinkedQueue<>();
        players.add(createPlayer("Alex", 3));
        players.add(createPlayer("Bob", 4));
        players.add(createPlayer("Chris", 5));
        simulator.setInitialPlayersList(players);
        GameManager gameManager = simulator.initialize();
        Assert.assertEquals(gameManager.getCurrentPlayer(), createPlayer("Alex", 3));
        Assert.assertEquals(gameManager.getPlayersLeft(), players);
    }

}
