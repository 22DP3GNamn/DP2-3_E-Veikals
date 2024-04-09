package rvt;

import org.springframework.validation.BindingResult;
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
            System.out.println("File not found" + e.getMessage());
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
}
