package dsa;

import edu.upc.dsa.minim.models.exceptions.*;
import edu.upc.dsa.minim.models.Game;
import edu.upc.dsa.minim.models.Partida;
import edu.upc.dsa.minim.models.User;
import edu.upc.dsa.minim.GameManager;
import edu.upc.dsa.minim.GameManagerImpl;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class GameServiceTest {
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);
    GameManager manager;

    @Before
    public void setUp() throws GameDoesNotExistException, PlayerCurrentlyPlayingException {
        manager = new GameManagerImpl();

        manager.addGame("pichi",2, 6);
        manager.addGame("so-pa-po",2, 2);

        manager.startGame("pichi", "alex");
    }

    @After
    public void tearDown()  {
        this.manager = null;
        logger.info("--- End of the test ---");
    }

    @Test
    public void startGame() throws GameDoesNotExistException, PlayerCurrentlyPlayingException {
        logger.info("--- Start of the test - Iniciar partida ---");

        logger.info("Matias intenta de iniciar una partida en un juego que no existe:");
        Assert.assertThrows(JuegoDoesNotExistException.class, ()-> manager.startGame("xavi", "pichi"));

        Partida p1 = manager.iniciarPartida("so-pa-po", "xavi");

        Assert.assertEquals("so-pa-po", p1.getNamejuego());
        Assert.assertEquals("matias", p1.getUsername());
        Assert.assertEquals("1", p1.getNivelActual().toString());
        Assert.assertEquals("50", p1.getPuntos().toString());

        logger.info("Matias intenta de iniciar otra partida mientras ya esta jugando a una:");
        Assert.assertThrows(PlayerCurrentlyPlayingException.class, ()->manager.iniciarPartida("pichi", "matias"));

    }
    @Test
    public void jugarPartida() throws JuegoDoesNotExistException, PlayerCurrentlyPlayingException, PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException {
        logger.info("--- Start of the test - Consultar nivel, puntuacion y terminar partida ---");

        Partida p1 = manager.iniciarPartida("so-pa-po", "matias");

        logger.info("Matias quiere saber en que nivel esta:");
        Assert.assertEquals("1", manager.getNivelActual("matias"));

        logger.info("Matias quiere saber cuantos puntos tiene:");
        Assert.assertEquals("50", manager.getPuntuacionActual("matias"));

        logger.info("Matias decide finalizar la partida:");
        Assert.assertEquals(false, manager.endPartida("matias").getCurrentlyPlaying());

        logger.info("Matias quiere volver a saber en que nivel esta (aunque ya no juega):");
        Assert.assertThrows(PlayerNotCurrentlyPlayingException.class,()->manager.getNivelActual("matias"));

        logger.info("Matias quiere volver a saber su puntuacion esta (aunque ya no juega):");
        Assert.assertThrows(PlayerNotCurrentlyPlayingException.class,()->manager.getPuntuacionActual("matias"));

        logger.info("Un username random quiere saber en que nivel:");
        Assert.assertThrows(PlayerDoesNotExistException.class,()->manager.getNivelActual("RANDOM"));

        logger.info("Un username random quiere saber su puntuacion:");
        Assert.assertThrows(PlayerDoesNotExistException.class,()->manager.getPuntuacionActual("RANDOM"));



    }

    @Test
    public void pasarNivel() throws JuegoDoesNotExistException, PlayerCurrentlyPlayingException, PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException {
        logger.info("--- Start of the test - Pasar nivel ---");

        Partida p1 = manager.iniciarPartida("so-pa-po", "matias");

        Assert.assertEquals("1", manager.getNivelActual("matias"));
        Assert.assertEquals("50", manager.getPuntuacionActual("matias"));

        logger.info("Matias sube de nivel:");
        manager.nextLevel("matias", 30,"30-02-2022");
        Assert.assertEquals("2",manager.getNivelActual("matias") );
        Assert.assertEquals("80", manager.getPuntuacionActual("matias"));

        logger.info("Matias quiere seguir subiendo de nivel pero ya esta en el mas alto:");
        manager.nextLevel("matias", 200,"31-02-2022");
        Assert.assertThrows(PlayerNotCurrentlyPlayingException.class,()->manager.getNivelActual("matias"));

        logger.info("Como Matias ya estaba en el nivel mas alto se le han sumado 100puntos y ha terminado de jugar:");
        Partida partidajugada = manager.getPartida("so-pa-po", "matias");
        Assert.assertEquals("180", partidajugada.getPuntos().toString());
        Assert.assertEquals(false, manager.getPlayer("matias").getCurrentlyPlaying());
    }

    @Test
    public void sort() throws JuegoDoesNotExistException, PlayerCurrentlyPlayingException, PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException {
        logger.info("--- Start of the test - Sort Players ---");

        manager.iniciarPartida("pichi", "matias");
        manager.iniciarPartida("pichi", "clara");
        manager.endPartida("alex");

        logger.info("Matias y Clara estan jugando al pichi\n Alex ha terminado de jugar");
        manager.nextLevel("clara", 23, "01-01-1992");
        manager.nextLevel("matias", 12, "02-01-1992");

        List<Player> sortedplayers = manager.sortPlayers(manager.getJuego("pichi"));
        Assert.assertEquals(3, sortedplayers.size());
        Assert.assertEquals("clara", sortedplayers.get(0).getUsername());
        Assert.assertEquals("matias", sortedplayers.get(1).getUsername());
        Assert.assertEquals("alex", sortedplayers.get(2).getUsername());

        logger.info("Nadie esta jugando al so-pa-po");
        List<Player> tryingsortplayers = manager.sortPlayers(manager.getJuego("so-pa-po"));
        Assert.assertEquals(null, tryingsortplayers);

        logger.info("El juego parchis no existe: ");
        Assert.assertThrows(JuegoDoesNotExistException.class, ()-> manager.sortPlayers(new Juego("parchis", "diver", 70)));
    }
    @Test
    public void getPartidasyPerformancePlayer() throws PlayerDoesNotExistException, PlayerNotCurrentlyPlayingException, JuegoDoesNotExistException, PlayerCurrentlyPlayingException {
        logger.info("--- Start of the test - Get partidas Players & get actividad---");

        manager.endPartida("alex");
        manager.iniciarPartida("pichi", "alex");
        manager.nextLevel("alex", 200,"01-03-2022");
        manager.nextLevel("alex", 30,"02-03-2022");

        logger.info("Alex quiere ver las partidas a las que ha jugado:");
        Assert.assertEquals(2, manager.getPartidasPlayer("alex").size());

        logger.info("Alex quiere ver su actividad en la ultima partida a la que ha jugado pichi:");
        Assert.assertEquals(2, manager.getPerformance("pichi","alex").size());

        logger.info("Alguien random quiere ver las partidas a las que ha jugado:");
        Assert.assertThrows(PlayerDoesNotExistException.class,()->manager.getPartidasPlayer("random"));

        logger.info("Alex random quiere ver su actividad en la ultima partida a la que ha jugado algo random:");
        Assert.assertThrows(JuegoDoesNotExistException.class, ()-> manager.getPerformance("random","random").size());
    }

}
