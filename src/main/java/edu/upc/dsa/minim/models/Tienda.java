package edu.upc.dsa.minim.models;

public class Tienda {
    private String productId;
    private String description;
    private int price;
    public Tienda(){}

    public Tienda( String productId, String description, int price){
        this();
        this.productId = productId;
        this.description = description;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
