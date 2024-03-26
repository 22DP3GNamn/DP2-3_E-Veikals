package rvt;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DefaultController {
    
    @GetMapping(value = "/")
     public ModelAndView index(@RequestParam HashMap<String, String> allParams){
        if (allParams.containsKey("success")) {
            ModelAndView modelAndView = new ModelAndView("shoppin");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("main");
        return modelAndView;
    } 

    @GetMapping(value = "/profile")
    ModelAndView profile(){
        ModelAndView modelAndView = new ModelAndView("about");
    return modelAndView;
    }

    @GetMapping(value = "/login")
    String Login(){
        return "login";
    }
    
    @GetMapping(value = "/YourCart")
    public String YourCart() {
        return "YourCart";
    }
    
    @GetMapping(value = "/shoppin")
    String shoppin(){
        return "shoppin";
    }

    @GetMapping(value = "/test")
    public ModelAndView testAction(){
        ModelAndView modelAndView = new ModelAndView("test");
        return modelAndView;
    }

    @GetMapping(value = "/successRegister")
    public String success() {
        return "login";
    }




    @GetMapping(value = "/register")
    public ModelAndView registerPage(@RequestParam HashMap<String, String> allParams){

        ModelAndView modelAndView = new ModelAndView("registration");
        
        Person person = new Person();

        modelAndView.addObject("person", person);

        return modelAndView;
    }

    @PostMapping("/register")
    public String registerForm(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult) {
        System.out.println(person.toString());
        if(bindingResult.hasErrors() || !person.getPassword().equals(person.getConfirmPassword())) {
            // Apstrādāt kļūdas
            return "/registration";
        }
        // Ja nav kļūdu, turpiniet izpildi
        return "redirect:/?success";
    }

    // public String register(String name, String surname, String email, String password, String confirmPassword, ModelMap model){
    //     if (!password.equals(confirmPassword)) {
    //         model.addAttribute("errorMessage", "Passwords do not match");
    //         return "/registration";
    //     } else{
    //         List<String> formData = Arrays.asList(name, surname, email, password);
    //         CSVManager csvManager = new CSVManager();
    //         csvManager.writeToCSV(formData, "src/main/data/PersonTable.csv");
    //         return "redirect:/"; 
    //     } 
    // }





    // @GetMapping(value = "/registration")
    // public ModelAndView register(@RequestParam HashMap<String, String> allParams, User user){
    //     if(allParams.containsKey("success")){
    //         ModelAndView modelAndView = new ModelAndView("success");
    //         return modelAndView;
    //     }
    //     ModelAndView modelAndView = new ModelAndView("registration");
    //     return modelAndView;
    // }

    // @PostMapping(value = "/registration")
    // public String register(@Valid @ModelAttribute("student")Person person, BindingResult bindingResult){
    //     if(bindingResult.hasErrors()){
    //         return "/registration";
    //     }
    //     else{
    //         System.out.println(person);
    //         return "redirect:/success";
    //     }
    // }
}



