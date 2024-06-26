package rvt;

import org.springframework.validation.BindingResult;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.servlet.http.HttpSession;
import lv.rvt.CheckoutForm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CSVManager {
    private static final String file_path = "src/main/data/PersonTable.csv";
    private static final String file_path_product = "src/main/data/Products.csv";
    private static final String file_path_checkout = "src/main/data/checkout.csv";
    private static final String file_path_product_list = "src/main/data/Product_list.csv";

    //login funkcija
    public static boolean login(String email, String password) {
        try {
            File inputFile = new File(file_path);
            try (Scanner scanner = new Scanner(inputFile)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] fields = line.split(",");
                    if (fields[2].equals(email) && fields[3].equals(password)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Konts nepastāv!" + e.getMessage());
        }
        return false;
    }
    //parbauda vai lietotājs ar šādu e-pastu jau eksistē
     public static boolean userEmailExists(String email){
        try{
            File inputFile = new File(file_path);
            try(Scanner scanner = new Scanner(inputFile)){
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    String[] fields = line.split(",");
                    if(fields[2].equals(email)){
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found" + e.getMessage());
        }
        return false;
     }
    //ieraksta lietotāju csv failā
    public static boolean userWrite(String name,String surname,String email,String password,String confirmPassword, BindingResult bindingResult, Person person) {
        if(bindingResult.hasErrors() || !person.getPassword().equals(person.getConfirmPassword())) {
            return false;
        }
        try {
            FileWriter fileWriter = new FileWriter("src/main/data/PersonTable.csv", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(name + "," + surname + "," + email + "," + password);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
       
    }
    //emaila maiņas funkcija
    public static boolean changeEmail(String email, String newEmail, HttpSession session) {
        File oldFile = new File(file_path);
        File newFile = new File("src/main/data/PersonTable_temp.csv");
    
        try (Scanner scanner = new Scanner(oldFile);
            PrintWriter writer = new PrintWriter(newFile)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (email != null && line.contains(email)) {
                    line = line.replace(email, newEmail);
                }
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    
        if (oldFile.delete()) {
            boolean renameSuccess = newFile.renameTo(oldFile);
            if (renameSuccess) {
                session.setAttribute("userEmail", newEmail);
            }
            return renameSuccess;
        } else {
            return false;
        }
    }
    //paroles maiņas funkcija
    public static boolean changePassword(String email, String password, String newPassword, HttpSession session) {
        File oldFile = new File(file_path);
        File newFile = new File("src/main/data/PersonTable_temp.csv");

        try (Scanner scanner = new Scanner(oldFile);
            PrintWriter writer = new PrintWriter(newFile)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains(email) && line.contains(password)) {
                    line = line.replace(password, newPassword);
                }
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    
        if (oldFile.delete()) {
            boolean renameSuccess = newFile.renameTo(oldFile);
            if (renameSuccess) {
                session.setAttribute("userPassword", newPassword);
            }
            return renameSuccess;
        } else {
            return false;
        }
    }
    //profila dzēšanas funkcija
    public static boolean deleteAccount(String email, HttpSession session) {
        File oldFile = new File(file_path);
        File newFile = new File("src/main/data/PersonTable_temp.csv");
    
        try (Scanner scanner = new Scanner(oldFile);
            PrintWriter writer = new PrintWriter(newFile)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (!line.contains(email)) {
                    writer.println(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        
        if (oldFile.delete()) {
            boolean renameSuccess = newFile.renameTo(oldFile);
            if (renameSuccess) {
                session.invalidate();
            }
            return renameSuccess;
        } else {
            return false;
        }
    }
    //iegūst lietotāju pēc e-pasta
    public static Person getPersonByEmail(String email) {
        try (Scanner scanner = new Scanner(new File(file_path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields.length > 2 && fields[2].equals(email)) { 
                    String name = fields[0]; 
                    String surname = fields[1]; 
                    String password = fields[3]; 
                    return new Person(name, surname, email, password);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    //iegūst visus lietotājus
    public static List<Person> getAllUsers() throws CsvValidationException{
        List<Person> users = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(file_path));
            CSVReader csvReader = new CSVReader(reader)) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                Person user = new Person();
                user.setName(nextRecord[0]);
                user.setSurname(nextRecord[1]);  
                user.setEmail(nextRecord[2]); 
                user.setPassword(nextRecord[3]);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    //iegūst produktus pēc nosaukuma
    public static List<String[]> getProductsSortedByName() throws CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(file_path_product))) {
            List<String[]> lines = reader.readAll();
            lines.sort(Comparator.comparing(line -> line[1]));
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //iegūst produktus no csv faila
    public static List<Product> readCSVProduct() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file_path_product))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] productData = line.split(",");
                int id = Integer.parseInt(productData[0]);
                String name = productData[1];
                String description = productData[2];
                double price = Double.parseDouble(productData[3]);
                Product product = new Product(id, name, description, price);
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
    //kārto produktus pēc augstākās cenas
    public static List<Product> sortProductsByHigherPrice(List<Product> products) {
        products.sort(Comparator.comparing(Product::getPrice).reversed());
        return products;
    }
    //kārto produktus pēc zemākās cenas
    public static List<Product> sortProductsByLowerPrice(List<Product> products) {
        products.sort(Comparator.comparing(Product::getPrice));
        return products;
    }
    //kārto produktus alfabētiski
    public static List<Product> getProductsSortedAtoZ(List<Product> products) {
        products.sort(Comparator.comparing(Product::getName));
        return products;
    }
    //filtrē produktus
    public static List<Product> filterProducts(String filter) {
        List<Product> products = readCSVProduct();
        return products.stream().filter(product -> product.getDescription().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
    }
    //dzēš lietotāju admina funkcija
    public static boolean deletePerson(String email) throws CsvValidationException {
        List<Person> persons = getAllUsers();
        Person personToDelete = null;
        System.out.println("Persons before deletion: " + persons);
    
        for (Person person : persons) {
            if (person.getEmail().equals(email)) {
                personToDelete = person;
                break;
            }
        }
        if (personToDelete == null) {
            System.out.println("Person to delete not found!");
            return false;
        }
        persons.remove(personToDelete);
        File tempFile = new File(file_path + "_temp");
        try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
            for (Person person : persons) {
                String line = String.format("%s,%s,%s,%s",
                    person.getName(), person.getSurname(), person.getEmail(), person.getPassword());
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        new File(file_path).delete();
        tempFile.renameTo(new File(file_path));
    
        return true;
    }
    //iegūst produktu pēc ID
    public static Product getProductById(int id) {
        try (CSVReader reader = new CSVReader(new FileReader(file_path_product))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (Integer.parseInt(line[0]) == id) {
                    return new Product(Integer.parseInt(line[0]), line[1], line[2], Double.parseDouble(line[3]));
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }
    //CSV faila raksta no checkout forma
    public static boolean writeCheckoutData(CheckoutForm form, Cart product) {
        try {
            System.out.println("Writing checkout data: " + form);
    
            FileWriter fileWriter = new FileWriter(file_path_checkout, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(form.getName() + ", " + form.getSurname() + ", " + form.getEmail() + ", " + form.getAddress() + ", " + form.getCard() + ", " + form.getExpiryDate() + ", " + form.getCvv());
            printWriter.close();
            FileWriter fileWriterProduct = new FileWriter(file_path_product_list, true);
            PrintWriter printWriterProduct = new PrintWriter(fileWriterProduct);
            printWriterProduct.print(form.getEmail() + ", ");
            for (Product products : product.getItems()) {
                printWriterProduct.print(products.getName() + ", ");
            }
            printWriterProduct.println();
            printWriterProduct.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
