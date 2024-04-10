package rvt;

import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CSVManager {
    private static final String file_path = "src/main/data/PersonTable.csv";

    public static boolean login(String email, String password) {

        try{
            File inputFile = new File(file_path);
            try(Scanner scanner = new Scanner(inputFile)){
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    String[] fields = line.split(",");
                    if(fields[2].equals(email) && fields[3].equals(password)){
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Konts nepastāv!" + e.getMessage());
        }
    return false;
    }

     public static boolean userEamailExists(String email){
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
                // Update the session or user authentication to recognize the new email
                session.setAttribute("userEmail", newEmail);
            }
            return renameSuccess;
        } else {
            return false;
        }
    }
    
    public static boolean changePassword(String password, String newPassword, HttpSession session) {
        File oldFile = new File(file_path);
        File newFile = new File("src/main/data/PersonTable_temp.csv");
        String userEmail = (String) session.getAttribute("userEmail");
    
        try (Scanner scanner = new Scanner(oldFile);
            PrintWriter writer = new PrintWriter(newFile)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields[2].equals(userEmail) && fields[3].equals(password)) {
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
                // Update the session or user authentication to recognize the new password
                session.setAttribute("userPassword", newPassword);
            }
            return renameSuccess;
        } else {
            return false;
        }
    }

    public static Person getPersonByEmail(String email) {
        File file = new File("src/main/data/PersonTable.csv");
    
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields[2].equals(email)) { 
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
}