package rvt;

import org.springframework.validation.BindingResult;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVManager {
    public List<List<String>> readFromCSV(String filePath) {
            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    records.add(Arrays.asList(values));
                }
            } catch (IOException e) {
                e.printStackTrace();
        }
        return records;
    } 



    public static boolean userExists(String email, String password) {
    CSVManager csvManager = new CSVManager();
        List<List<String>> records = csvManager.readFromCSV("src/main/data/PersonTable.csv");

        for (List<String> record : records) {
            String _email = record.get(2);
            String _password = record.get(3);

            if(email.equals(_email) && password.equals(_password)){
                return true;
            }else{
                return false;
            }
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
