package rvt;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
            return "/registration";
        }
        try {
            FileWriter fileWriter = new FileWriter("src/main/data/PersonTable.csv", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(person.getName() + "," + person.getSurname() + "," + person.getEmail() + "," + person.getPassword());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/?success";
    }

    @GetMapping(value = "/login")
    public ModelAndView loginPage(@RequestParam HashMap<String, String> allParams){
        ModelAndView modelAndView = new ModelAndView("login");
        Person person = new Person();
        modelAndView.addObject("person", person);
        return modelAndView;
    }

    @PostMapping(value = "/login")
    public String loginForm(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult){
        if(bindingResult.getFieldError("email") != null || bindingResult.getFieldError("password") != null){
            return "/login";
        }
        if (CSVManager.userExists(person.getEmail(), person.getPassword())){
            return "redirect:/?success";
        }
        return "/login";
    }
}