package edu.upc.dsa.minim;
import edu.upc.dsa.minim.models.Partida;
import edu.upc.dsa.minim.models.User;
import edu.upc.dsa.minim.models.exceptions.*;

public interface GameManager {
    public int numUsers();
    public int numGames();
    public void addGame(String gameId, int equipos, int personas) throws GameAlreadyExistsException;
    public void addUser(String userId, String nombre, String apellidos);
    public Partida startGame(String userId, String gameId) throws GameDoesNotExistException, UserDoesNotExistException, AlreadyActiveActivityException;
    public int currentLifes(String userId) throws UserDoesNotExistException, NoGameActiveException;
    public int currentLifesTeam(String gameId, Integer equipo);
    public User endGame(String userIdName) throws UserDoesNotExistException;
    public void addProduct(String productId, String description, Integer price);
    public void getProduct(String userId, String productId) throws NotEnoughCoinsException, UserDoesNotExistException, ProductDoesNotExistException;
    public int size();
}
