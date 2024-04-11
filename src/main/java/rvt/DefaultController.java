package rvt;

import java.util.HashMap;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class DefaultController {
    String regexName = "^[A-Z][a-z]+$";
    String regexSurname = "^[A-Z][a-z]+$";
    String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    String regexPassword = "^.{5,20}$";


    @GetMapping(value = "/")
    public ModelAndView index(@RequestParam HashMap<String, String> allParams){
        if (allParams.containsKey("success")) {
            ModelAndView modelAndView = new ModelAndView("shoppin");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("main");
        return modelAndView;
    } 

    @GetMapping(value = "/test")
    public ModelAndView testAction(){
        ModelAndView modelAndView = new ModelAndView("test");
        return modelAndView;
    }

    
    @GetMapping(value = "/YourCart")
    public String YourCart() {
        return "YourCart";
    }
    
    @GetMapping(value = "/shoppin")
    String shoppin(){
        return "shoppin";
    }

    
    @GetMapping(value = "/successRegister")
    public String success() {
        return "login";
    }
    @GetMapping(value = "/userProfile")
    public String profilePage() {
        return "userProfile";
    }

    @GetMapping(value = "/register")
    public ModelAndView registerPage(@RequestParam HashMap<String, String> allParams){
        ModelAndView modelAndView = new ModelAndView("registration");
        Person person = new Person();
        modelAndView.addObject("person", person);
        return modelAndView;
    }

    @PostMapping(value="/register")
    public String registerForm(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult) {
        if(CSVManager.userEmailExists(person.getEmail())){
            bindingResult.rejectValue("email", "error.email", "E-pasts jau ir reģistrēts!");
            return "/registration";
        }
        if(!person.getPassword().equals(person.getConfirmPassword())){
            bindingResult.rejectValue("password", "error.confirmPassword", "Paroles nesakrīt!");
            return "/registration";
        }
        if(CSVManager.userWrite(person.getName(), person.getSurname(), person.getEmail(), person.getPassword(), person.getConfirmPassword(), bindingResult, person)){
            return "redirect:/?success";
        }
        return "/registration";
    }

    @GetMapping(value = "/login")
    public ModelAndView loginPage(@RequestParam HashMap<String, String> allParams){
        ModelAndView modelAndView = new ModelAndView("login");
        Person person = new Person();
        modelAndView.addObject("person", person);
        return modelAndView;
    }

    @PostMapping(value="/login")
    public String loginForm(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.getFieldError("email") != null || bindingResult.getFieldError("password") != null){
            return "/login";
        }
        if(!CSVManager.userEmailExists(person.getEmail())){
            bindingResult.rejectValue("email", "error.email", "E-pasts nav reģistrēts!");
            return "/login";
        }
        if(!CSVManager.login(person.getEmail(), person.getPassword())){
            bindingResult.rejectValue("password", "error.password", "Nepareiza parole!");
            return "/login";
        }
        if (CSVManager.login(person.getEmail(), person.getPassword())){
            Person fullPerson = CSVManager.getPersonByEmail(person.getEmail());
            request.getSession().setAttribute("person", fullPerson);
            return "redirect:/?success";
        }
        return "/login";
    }

    @GetMapping(value="/profile")
    public ModelAndView profile(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("profile");
        Person person = (Person) request.getSession().getAttribute("person");
        if (person != null) {
            modelAndView.addObject("name", person.getName());
            modelAndView.addObject("surname", person.getSurname());
            modelAndView.addObject("email", person.getEmail());
            modelAndView.addObject("password", person.getPassword());
        }
        return modelAndView;
    }

    @PostMapping(value="/changeEmail")
    public String changeEmail(@RequestParam("newEmail") String newEmail, HttpServletRequest request) {
        Person person = (Person) request.getSession().getAttribute("person");
        if (person != null) {
            CSVManager.changeEmail(person.getEmail(), newEmail, request.getSession());
            person.setEmail(newEmail);
            request.getSession().setAttribute("person", person);
        }
        return "redirect:/profile";
    }

    @PostMapping(value = "/changePassword")
    public String changePassword(String email, @RequestParam("newPassword") String newPassword, HttpServletRequest request) {
        Person person = (Person) request.getSession().getAttribute("person");
        if (person != null) {
            CSVManager.changePassword(person.getEmail(), person.getPassword(), newPassword, request.getSession());
            person.setPassword(newPassword);
            request.getSession().setAttribute("person", person);
        }
        return "redirect:/profile";
    }

    @PostMapping(value = "/deleteAccount")
    public String deleteAccount(HttpServletRequest request) {
        Person person = (Person) request.getSession().getAttribute("person");

        if (person != null) {
            String email = person.getEmail();
            boolean deleteSuccess = CSVManager.deleteAccount(email, request.getSession());

            if (deleteSuccess) {
                return "redirect:/login"; 
            } else {
                return "redirect:/profile";
            }
        } else {
            return "redirect:/profile";
        }
    }

    @PostMapping(value="/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}