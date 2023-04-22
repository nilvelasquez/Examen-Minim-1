package edu.upc.dsa.models;

public class Product {
    String productId;
    String description;
    double price;
    public Product(){}
    public Product(String id, String description, double price){
        this.productId = id;
        this.description=description;
        this.price=price;
    }
    public String getProductId(){return this.productId;}
    public void setProductId(String id){this.productId=id;}
    public String getDescription() {return description;}
    public void setDescription(String description){this.description=description;}
    public double getPrice(){return this.price;}
    public void setPrice(double price){this.price=price;}
}
