package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.util.List;

public interface GameManager {
    String addGame(String gameId, int equipos, int personas);
    void addUser(String userId, String nombre, String apellido);
    void addProduct(String productId, String description, double price);
    void comprarProduct(String productId, String userId);
    String startPartida(String partyId, String userId);
    String getEstado(String partyId);
    int getLifes(String userId);
    String endGame(String partyId);
    int numUsers();
    int numProducts();
}
