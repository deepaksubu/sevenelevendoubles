package sevenelevendoubles.core;

import junit.framework.Assert;
import org.junit.Test;
import sevenelevendoubles.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static sevenelevendoubles.entity.Player.createPlayer;

/**
 * User: deepak
 * Date: 3/8/14
 */
public class SimulatorTest {

    @Test
    public void testInitializeSimulation() throws Exception {
        Simulator simulator = new Simulator(new RandomizedSelector());
        List<Player> players = new ArrayList<>();
        players.add(createPlayer("Alex", 3));
        players.add(createPlayer("Bob", 4));
        players.add(createPlayer("Chris", 5));
        simulator.setInitialPlayersList(players);
        GameManager gameManager = simulator.createGameManager();
        Assert.assertEquals(gameManager.getPlayers().get(0), createPlayer("Alex", 3));
        Assert.assertEquals(gameManager.getPlayers(), players);
    }

}
