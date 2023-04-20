package edu.upc.dsa.minim.models;

public class VOPlayerGameCredencials {
    String nameJuego;
    String userId;

    public VOPlayerGameCredencials() {
        //this.id = RandomUtils.getId();
    }

    public VOPlayerGameCredencials(String namejuego, String username) {
        this.setNameJuego(namejuego);
        this.setUsername(username);
    }

    public String getNameJuego() {
        return nameJuego;
    }

    public void setNameJuego(String nameJuego) {
        this.nameJuego = nameJuego;
    }

    public String getUsername() {
        return userId;
    }

    public void setUsername(String userId) {
        this.userId = userId;
    }
}