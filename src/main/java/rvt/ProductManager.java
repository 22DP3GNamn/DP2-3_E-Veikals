// package rvt;
// import java.io.*;
// import java.nio.file.*;
// import java.util.*;

// public class ProductManager {

    

//     private static Map<String, Product> products = new HashMap<>();
//     private static String csvFile = "src/main/data/Products.csv";

//     static {
//         updateProductList();
//     }

//     private static void updateProductList() {
//         try {
//             List<String> lines = Files.readAllLines(Paths.get(csvFile));
//             for (String line : lines) {
//                 String[] parts = line.split(",");
//                 String id = parts[0];
//                 String name = parts[1];
//                 String description = parts[2];
//                 double price = Double.parseDouble(parts[3]);
//                 products.put(id, new Product(id, name, description, price));
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public static Product findProductById(String id) {
//         return products.get(id);
//     }
// }