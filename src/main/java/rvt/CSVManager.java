package rvt;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.PrintWriter;
// import java.util.ArrayList;
// import java.util.Scanner;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

public class CSVManager{
    public void writeToCSV(List<String> data, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            for (String item : data) {
                writer.append(item);
                writer.append(",");
            }
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




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
}