package rvt;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.PrintWriter;
// import java.util.ArrayList;
// import java.util.Scanner;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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




    @WebServlet("/RegisterServlet")
    public class RegisterServlet extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmpassword");
    
            if (!password.equals(confirmPassword)) {
                response.sendRedirect("error.html");
                return;
            }
    
            Person person = new Person(name, surname, email, password);
            boolean isAdded = CSVManager.addPersonToCsv(person);
    
            if (isAdded) {
                response.sendRedirect("success.html");
            } else {
                response.sendRedirect("error.html");
            }
        }
    
    
}
}
