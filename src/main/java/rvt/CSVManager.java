package rvt;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.PrintWriter;
// import java.util.ArrayList;
// import java.util.Scanner;

public class CSVManager{

    public static boolean addPersonToCsv(Person person){
        try{
            BufferedWriter writer = 
                new BufferedWriter(
                    new FileWriter("/workspaces/DP2-3_E-Veikals/src/main/data/PersonTable.csv", true));
            
                String record = String.format("%s, %s, %s, %s\n",person.getname(), person.getsurname(), person.getemail());
                writer.write(record);
            
            writer.close();
    
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    // public static void removePersonFromCSV(String personToRemove){
    //     ArrayList<String> persona = new ArrayList<>();
    //     try{
    //         File inputFile= new File("/workspaces/DP2-3_E-Veikals/src/main/data/PersonTable.csv");
    //         Scanner scanner = new Scanner(inputFile);
    //         while (scanner.hasNextLine()){
    //             String line = scanner.nextLine();
    //             if (!line.contains(personToRemove)){
    //                 persona.add(line);

    //             } 
    //         }

    //         scanner.close();
    //         PrintWriter writer = new PrintWriter(inputFile);
    //         for(String person : persona){
    //             writer.println(person);
    //         }
    //         writer.close();
    //     } catch(FileNotFoundException e) {
    //         System.out.println("Kļūda!");
    //         e.printStackTrace();
    //     }

    // }
    
}
