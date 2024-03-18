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
            
                String record = String.format("%s, %s, %s, %s\n",person.getname(), person.getsurname(), person.getemail(), person.getpassword());
                writer.write(record);
                
            writer.flush();
            writer.close();
    
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
}
