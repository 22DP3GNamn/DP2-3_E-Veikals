package rvt;

import jakarta.validation.Valid;
import java.util.List;
import java.util.HashMap;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@Controller
public class DefaultController {

    private List<Product> products;

    @GetMapping(value = "/")
    public ModelAndView index(@RequestParam HashMap<String, String> allParams){
        if (allParams.containsKey("success")) {
            ModelAndView modelAndView = new ModelAndView("shoppin");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("main");
        return modelAndView;
    } 

    @PostMapping("/allUserManager")
    public String handleAllUserManager() {
        return "redirect:/allUserManager";
    }

    @GetMapping(value = "/test")
    public ModelAndView testAction(){
        ModelAndView modelAndView = new ModelAndView("test");
        return modelAndView;
    }

    @GetMapping("/allUserManager")
    public String allUserManager(Model model) throws CsvValidationException {
        List<Person> users = CSVManager.getAllUsers();
        model.addAttribute("users", users);
        return "allUserManager";
    }
    
    @GetMapping(value = "/YourCart")
    public String YourCart() {
        return "YourCart";
    }
    
    @GetMapping(value = "/shoppin")
    public ModelAndView Shoppin(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        products = CSVManager.readCSVProduct();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("/shoppin"); 
        return modelAndView;
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
            return "redirect:/login";
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
            return "redirect:/profile";
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
            if(Validator.validateEmail(newEmail)){
                CSVManager.changeEmail(person.getEmail(), newEmail, request.getSession());
                person.setEmail(newEmail);
                request.getSession().setAttribute("person", person);
            }
        }
        return "redirect:/profile";
    }

    @PostMapping(value = "/changePassword")
    public String changePassword(String email, @RequestParam("newPassword") String newPassword, HttpServletRequest request) {
        Person person = (Person) request.getSession().getAttribute("person");
        if (person != null) {
            if(Validator.validatePassword(newPassword))
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

    @PostMapping(value="/shoppin")
    public String sortingProducts(@RequestParam("sort") String sort, Model model) {
        if("higher".equals(sort)) {
            products = CSVManager.sortProductsByHigherPrice();
        } else if("lower".equals(sort)) {
            products = CSVManager.sortProductsByLowerPrice();
        }else if("A-Z".equals(sort)) {
            products = CSVManager.getProductsSortedAtoZ();
        } else {
            products = CSVManager.readCSVProduct();
        }
        model.addAttribute("products", products);
        return "shoppin";
    }
}