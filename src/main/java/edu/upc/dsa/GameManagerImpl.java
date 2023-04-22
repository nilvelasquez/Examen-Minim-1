package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import javax.servlet.http.Part;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;
    protected List<User> user;
    protected List<Partida> partida;
    protected List<Product> product;
    HashMap<String, String> Games =new HashMap<String,String>();
    LinkedList<String> listaGames = new LinkedList<String>();
    LinkedList<String> listaUsers = new LinkedList<String>();
    LinkedList<String> listaProducts = new LinkedList<String>();
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    private GameManagerImpl() {
        this.user = new LinkedList<>();
    }

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }
    @Override
    public String addGame(String gameId, int equipos, int personas) {
        Partida partida = getPartidaByGameId(gameId);
        String estado = partida.getEstado();
        if (listaGames.contains(gameId)){
            logger.error("New Game: " + gameId);
            estado = "NO_INICIADO";
        }
        else {
            Game newGame = new Game(gameId, equipos, personas);
            listaGames.add(gameId);
            logger.info("Game created with: " + equipos+ " teams and "+personas+" people.");
            estado = "INICIADO_EN_PREPARACIÓN";

        }
        return estado;
    }
    @Override
    public void addUser(String userId, String nombre, String apellido) {
        User user;
        logger.info("Check if the user "+userId+" already exists.");
        if(listaUsers.contains(userId)) {
            logger.error("This user already exists.");
        }
        else {
            logger.info("We add the user: "+userId);
            this.user.add(new User(userId,nombre,apellido));
            listaUsers.addLast(userId);
        }
    }
    @Override
    public void addProduct(String productId, String description, double price) {
        logger.info("Check if the product "+productId+" already exists.");
        if(listaProducts.contains(productId)) {
            logger.error("This product already exists.");
        }
        else {
            logger.info("We add the product: "+productId);
            this.product.add(new Product(productId,description,price));
            listaProducts.addLast(productId);
        }
    }

    @Override
    public void comprarProduct(String productId, String userId)  {
        logger.info("We check if the user "+userId+" and the product "+productId+" exists.");
        if(listaUsers.contains(userId) && listaProducts.contains(productId)){
            Product product = getProductForProductId(productId);
            User user = getUserByUserId((userId));
            if (user.getDsaCoins() < product.getPrice()) {
                logger.info("Not enough coins.");
            }
            else {
                user.getListaBuyedProducts().add(product);
                double dsaCoins = user.getDsaCoins() - product.getPrice();
                user.setDsaCoins(dsaCoins);
                logger.info("Object " + productId + " buyed.");
                logger.info(userId + " haves: " + product + " dsaCoins");
            }
        }
        else{
            logger.info("The user and/or the product doesn't exist.");
        }
    }
    @Override
    public String startPartida(String gameId, String userId) {
        Partida partida = getPartidaByGameId(gameId);
        String estado = partida.getEstado();
        boolean encontrado = false;
        if(listaGames.contains(gameId) && listaUsers.contains(userId)) {
            logger.info("There is already a game with the id: " + gameId + " played for the user with id: " + userId);
            if (Games.containsValue(gameId) && gameId.equals(Games.get((userId)))) {
                logger.error("The user is already in a game.");
            }
            else {
                estado = "INICIADO_EN_FUNCIONAMIENTO";
                encontrado = true;
            }
        }

        else {
            logger.error("The user and/or the product doesn't exist.");
        }
        if (encontrado){
            logger.info("The game started correctly.");
            Games.put(gameId,userId);
        }
        return estado;
    }
    public String getEstado(String gameId){
        String result = null;
        Partida partida = getPartidaByGameId(gameId);
        String estado = partida.getEstado();
        if(listaGames.contains(gameId)) {
            logger.info("There is a game with id: "+gameId);
            result = estado;
        }
        else{
            logger.error("The party doesn't exists.");
        }
        return result;
    }
    @Override
    public int getLifes(String userId) {
        int result=0;
        if(listaUsers.contains(userId) && Games.containsKey(userId)) {
            logger.info("The user with id: " + userId + " is on a game");
            User user = getUserByUserId((userId));
            int vida = user.getLife();
            result = vida;
        }
        else{
            logger.error("The user and/or the product doesn't exist.");
            result = -1;
        }
        return result;
    }
    @Override
    public String endGame(String gameId) {
        Partida partida = getPartidaByGameId(gameId);
        String estado = partida.getEstado();
        if (listaGames.contains(gameId) && Games.containsKey(gameId)){
            logger.info("There is a game with id: "+gameId);
            for(Map.Entry<String,String> entry : Games.entrySet()) {
                if (entry.getKey().equals(gameId)) {
                    Games.remove(gameId);
                    logger.info("The game has end.");
                    estado = "FINALIZADO";
                    break;
                }
            }
        }
        else{
            logger.error("No existe el usuario o no se encuentra en partida");
        }
        return  estado;
    }
    @Override
    public int numUsers() {
        return this.listaUsers.size();
    }

    @Override
    public int numProducts() {
        return this.listaProducts.size();
    }
    public User getUserByUserId(String userId){
        for (User u: this.user) {
            if(u.getUserId().equals(userId)){
                return u;
            }
        }
        return null;
    }
    public Partida getPartidaByGameId(String partyId){
        for (Partida p: this.partida) {
            if(p.getPartyId().equals(partyId)){
                return p;
            }
        }
        return null;
    }
    public Product getProductForProductId(String idProduct){
        for (Product p: this.product) {
            if(p.getProductId().equals(idProduct)){
                return p;
            }
        }
        return null;
    }
}