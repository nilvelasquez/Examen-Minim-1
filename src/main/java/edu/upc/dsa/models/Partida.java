package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Partida {
    String partyId;
    String userId;
    String estado=null;
    ArrayList<Game> partidas = new ArrayList<Game>();
    public Partida() {}
    public Partida(String userId, String partyId){
        this.partyId = partyId;
        this.userId = userId;
    }
    public String getPartyId() {return partyId;}
    public void setPartyId(String partyId) {this.partyId = partyId;}
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}
    public ArrayList<Game> getPartidas() {return partidas;}
}
