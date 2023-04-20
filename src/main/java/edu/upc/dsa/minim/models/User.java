package edu.upc.dsa.minim.models;

import edu.upc.dsa.minim.models.exceptions.NoGameActiveException;
import edu.upc.dsa.minim.models.exceptions.AlreadyActiveActivityException;

import java.util.*;

public class User {

    private String userId;
    private String nombre;
    private String apellidos;
    private Integer lifes;
    private Integer dsaCoins;
    private Boolean active;
    private Map<String, Partida> partidas;

    public User() {
        this.active = false;
        this.partidas = new HashMap<>();
    }

    public User(String userId) {
        this.userId = userId;
    }


    public User(String nombre, String apellidos) {
        this.setNombre(nombre);
        this.setApellidos(apellidos);
    }

    public String getUserId() {
        return this.userId;
    }

    public void setLifes(Integer lifes) {
        lifes = lifes;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDsaCoins() {
        return this.dsaCoins;
    }

    public void setDsaCoins(int dsaCoins) {
        this.dsaCoins = dsaCoins;
    }
        public Boolean getActive () {
            return active;
        }


        public void setActive (Boolean active){
            this.active = active;
        }

        public Map<String, Partida> getPlays () {
            return partidas;
        }

        public void setPlays (Map < String, Partida > partidas){
            this.partidas = partidas;
        }

        public String getNombre () {
            return nombre;
        }

        public void setNombre (String nombre){
            this.nombre = nombre;
        }

        public String getApellidos () {
            return apellidos;
        }

        public void setApellidos (String apellidos){
            this.apellidos = apellidos;
        }

        public int getLifes () throws NoGameActiveException {
            if (!this.active) {
                throw new NoGameActiveException();
            }
            return currentGamePlay().getLifes();
        }

        public String getCurrentGame () throws NoGameActiveException {
            if (!this.active) {
                throw new NoGameActiveException();
            }
            return currentGamePlay().getGame();
        }
        public void endGame () {
            this.active = false;
        }
        public void startGame (Game game) throws AlreadyActiveActivityException {
            if (this.active) {
                throw new AlreadyActiveActivityException();
            }
            this.partidas.put(game.getGameId(), new Partida(game));
            this.mostRecentPartida = game.getGameId();
            this.active = true;
        }
}

