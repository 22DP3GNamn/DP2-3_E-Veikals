package rvt;
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
import java.io.*;

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

        public static Person getPersonByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("/workspaces/DP2-3_E-Veikals/src/main/data/PersonTable.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[2].equals(email)) {
                    return new Person(parts[0], parts[1], parts[2], parts[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    }
        



//     @WebServlet("/RegisterServlet")
//     public class RegisterServlet extends HttpServlet {
//         protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//             String name = request.getParameter("name");
//             String surname = request.getParameter("surname");
//             String email = request.getParameter("email");
//             String password = request.getParameter("password");
//             String confirmPassword = request.getParameter("confirmpassword");

//             System.out.println("Name: " + name);
//             System.out.println("Surname: " + surname);
//             System.out.println("Email: " + email);
//             System.out.println("Password: " + password);
//             System.out.println("Confirm Password: " + confirmPassword);
    
//             if (!password.equals(confirmPassword)) {
//                 response.sendRedirect("error.html");
//                 return;
//             }
    
//             Person person = new Person(name, surname, email, password);
//             boolean isAdded = CSVManager.addPersonToCsv(person);
    
//             if (isAdded) {
//                 response.sendRedirect("success.html");
//             } else {
//                 response.sendRedirect("error.html");
//             }
//         }
    
    
// }

