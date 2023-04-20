package edu.upc.dsa.minim.models;

import java.util.ArrayList;

public class Partida {
    private String gameId;
    private Integer equipos;
    private Integer personas;
    public Partida(){}

    public Partida(Game game, Integer equipos, Integer personas){
        this.gameId = game.getGameId();
        this.equipos = equipos;
        this.personas = personas;
    }

    public String getGameId() { return gameId; }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    public Integer getEquipos() {
        return equipos;
    }
    public void setEquipos(int equipos) {
        this.equipos = equipos;
    }
    public Integer getPersonas() {
        return personas;
    }
    public void setPersonas(int personas) {
        this.personas = personas;
    }
}
