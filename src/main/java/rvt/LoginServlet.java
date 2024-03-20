package rvt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Person person = CSVManager.getPersonByEmail(email);

        if (person == null || !person.getpassword().equals(password)) {
            response.sendRedirect("/errorlogin");
        } else {
            response.sendRedirect("/successlogin");
        }
    }
}
