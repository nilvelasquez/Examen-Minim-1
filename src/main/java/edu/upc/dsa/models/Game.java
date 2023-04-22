package edu.upc.dsa.models;

import java.util.*;

public class Game {

    String gameId;
    int equipos;
    int personas;
    public Game() {}
    public Game(String id, int equipos, int personas) {
        this.gameId = id;
        this.equipos = equipos;
        this.personas = personas;
    }
    public String getGameId() {
        return this.gameId;
    }
    public void setGameId(String id) {
        this.gameId = id;
    }
    public int getEquipos() {return this.equipos;}
    public void setEquipos(int equipos) {this.equipos = equipos;}
    public int getPersonas() {return this.personas;}
    public void setPersonas(int personas) {this.personas = personas;}
    @Override
    public String toString() {
        return "Juego [Número de equipos="+equipos+", Número de personas=" + personas + "]";
    }
}