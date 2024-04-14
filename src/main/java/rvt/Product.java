package rvt;

public class Product {
    private int id;
    private String productName;
    private String description;
    private double price;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getproductName() {
        return productName;
    }
    
    public void setproductName(String productName) {
        this.productName = productName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    
    public Product(int id, String productName, String description, double price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }
}