package dsa;

import edu.upc.dsa.GameManager;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;
public class GameManagerTest {
    private static Logger logger = Logger.getLogger(GameManagerTest.class);
    GameManager manager = null;
    @Test
    public void addUserTest(){
        manager.addUser("1","Nil","Velàsquez");
        manager.addUser("2","Borja","Fernández");
        Assert.assertEquals(2,manager.numUsers());;
    }
    @Test
    public void addProductTest(){
        manager.addProduct("1","Life potion",15);
        manager.addProduct("2","Book",50);
        Assert.assertEquals(2,manager.numProducts());
    }
    @Test
    public void getEstadoTest(){
        manager.getEstado("1");
        Assert.assertEquals("INICIADO_EN_FUNCIONAMIENTO",manager.getEstado("1"));
    }
    @Test
    public void getLifesTest(){
        manager.addUser("1","Nil","Velàsquez");
        manager.getLifes("1");
        Assert.assertEquals(100,manager.getLifes("1"));
    }
}