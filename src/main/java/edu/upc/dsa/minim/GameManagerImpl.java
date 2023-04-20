package edu.upc.dsa.services;


import edu.upc.dsa.minim.GameManager;
import edu.upc.dsa.minim.GameManagerImpl;
import edu.upc.dsa.minim.models.*;
import edu.upc.dsa.minim.models.exceptions.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;

    protected List<Game> games;
    protected List<Tienda> productos;
    protected Map<String, User> users;
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);
    public GameManagerImpl(){
        this.games = new ArrayList<>();
        this.users = new HashMap<>();
        this.productos = new ArrayList<>();
    }
    @Override
    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }
    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }
   @Override
   public Game addGame(String id) {
       Game game = getGame(id);
       isGameNull(game);
       this.games.add(new Game(id));
       logger.info("New juego: " + game);
       return game;
   }
    @Override
    public void addUser(String userIdName) {
        this.users.put(userIdName, new User(userIdName));
    }

    @Override
    public Partida startGame(String userIdName, String gameId) throws GameDoesNotExistException, UserDoesNotExistException, AlreadyActiveActivityException {
        logger.info("Starting a new game with userId: "+userIdName+" and gameId: "+gameId);
        Game game = getGame(gameId);
        User user = getUser(userIdName);
        isGameNull(game);
        isUserNull(user);
        user.startGame(game);
        logger.info("The game was correctly started!");
        return null;
    }
    @Override
    public int currentLifes(String userId) throws UserDoesNotExistException, NoGameActiveException {
        logger.info("Getting current lifes of game of userId: "+ userId);
        User user = getUser(userId);
        isUserNull(user);
        int lifes = user.getLifes();
        logger.info("User current lifes are: "+lifes);
        return lifes;
    }
    @Override
    public User endGame(String userId) throws UserDoesNotExistException {
        logger.info("Ending current game of userId: "+userId);
        User user = getUser(userId);
        isUserNull(user);
        user.endGame();
        logger.info("End of game was correctly effectuated!");
        return user;
    }
    public void addProduct(String productId, String description, Integer price) {
        logger.info("Adding new product to the store");
        this.productos.add(new Tienda(productId,description,price));
    }
    public void getProduct(String userId, String productId) throws NotEnoughCoinsException, UserDoesNotExistException, ProductDoesNotExistException{
        logger.info("Getting product: "+ productId);
        isProductNull(productId);
        Integer price = getPrice(productId);
        Integer dsaCoins = getDsaCoins(userId);
        if (price<=dsaCoins) {
            logger.info("Product Obtained");
        }
        else
            throw new NotEnoughCoinsException();

    }
    public Game getGame(String gameId) {
        return this.games.stream()
                .filter(x->gameId.equals(x.getGameId()))
                .findFirst()
                .orElse(null);
    }

    public User getUser(String userIdName) {
        return this.users.get(userIdName);
    }
    public Tienda getPrice(Integer price){
        return this.productos.get(price);
    }
    public User getDsaCoins(Integer dsaCoins){
        return this.users.get(dsaCoins);
    }
    public void isGameNull(Game game) throws GameDoesNotExistException {
        if(game==null) {
            logger.warn("Game does not exist EXCEPTION");
            throw new GameDoesNotExistException();
        }
    }

    public void isUserNull(User user) throws UserDoesNotExistException {
        if(user==null) {
            logger.warn("User does not exist EXCEPTION");
            throw new UserDoesNotExistException();
        }
    }
    public void isProductNull(Tienda product) throws ProductDoesNotExistException {
        if(product==null) {
            logger.warn("Product does not exist EXCEPTION");
            throw new ProductDoesNotExistException();
        }
    }

    public int numUsers(){
        return this.users.size();
    }

    public int numGames(){
        return this.games.size();
    }
}