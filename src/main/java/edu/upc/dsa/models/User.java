package edu.upc.dsa.models;
import edu.upc.dsa.utils.RandomUtils;
import java.util.*;

public class User {
    String userId;
    String nombre;
    String apellido;
    int life=100;
    double dsaCoins = 0;
    List<Product> listaBuyedProducts = null;
    public User(String id, String nombre, String apellido) {
        this.userId = id;
        this.nombre = nombre;
        this.apellido = apellido;
        dsaCoins = 25;
    }
    public User() {this.userId = RandomUtils.getId();}
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}
    public double getDsaCoins() {return dsaCoins;}
    public void setDsaCoins(double dsaCoins) {this.dsaCoins = dsaCoins;}
    public List<Product> getListaBuyedProducts() {
        return listaBuyedProducts;
    }
    public void setListaBuyedProducts(List<Product> listaBuyedProducts) {
        this.listaBuyedProducts = listaBuyedProducts;
    }
    public int getLife() {return life;}
    public void setLife(int life) {this.life = life;}
}