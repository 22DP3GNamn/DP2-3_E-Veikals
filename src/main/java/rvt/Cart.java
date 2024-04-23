package rvt;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> items = new ArrayList<>();

    public void addProduct(Product product) {
        items.add(product);
    }

    public List<Product> getItems() {
        return items;
    }

    
}