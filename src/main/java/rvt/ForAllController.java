package rvt;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ForAllController {


    @PostMapping("/register")
    public String register(@RequestParam("name") String name, @RequestParam("surname") String surname,  @RequestParam("email") String email,
                            @RequestParam("password") String password, @RequestParam("confirmpassword") String confirmPassword, ModelMap model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match");
            return "registration";
        }else{
            List<String> formData = Arrays.asList(name, surname, email, password);
            CSVManager csvManager = new CSVManager();
            csvManager.writeToCSV(formData, "src/main/data/PersonTable.csv");
            return "redirect:/"; 
        }
    }



    @GetMapping("/login2")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, ModelMap model) {
        
        CSVManager csvManager = new CSVManager();
        List<List<String>> users = csvManager.readFromCSV("src/main/data/PersonTable.csv");
        for (List<String> user : users) {
            System.out.println("User email: " + user.get(2));
            System.out.println("User password: " + user.get(3));
            if (user.get(2).equals(email) && user.get(3).equals(password)) {
                return "redirect:/";
            }
        }
    
        model.addAttribute("errorMessage", "Invalid email or password");
        return "login";
    }
}