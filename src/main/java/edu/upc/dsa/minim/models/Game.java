package edu.upc.dsa.minim.models;

import java.security.PrivateKey;

public class Game {
    private String gameId;
    private String estado;
    public Game(){}

    public Game( String gameId){
        this();
        this.gameId = gameId;
    }
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
