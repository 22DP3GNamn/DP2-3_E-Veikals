package rvt;

import java.util.HashMap;

import javax.validation.Valid;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {
    
   @GetMapping(value = "/")
    String index(@RequestParam(name="name", required=false, defaultValue="null") String name, Model model) {
        return "main";
    }

    @GetMapping(value = "/main")
    String main(){
        return "main";
    } 

    @GetMapping(value = "/profile")
    ModelAndView profile(){
        ModelAndView modelAndView = new ModelAndView("about");
    return modelAndView;
    }

    @GetMapping(value = "/Login")
    String Login(){
        return "Login";
    }

    @GetMapping(value = "/Cart")
    String cart(){
        return "Cart";
    }

    @GetMapping(value = "/test")
    public ModelAndView testAction(){
        ModelAndView modelAndView = new ModelAndView("test");
        return modelAndView;
    }

    @GetMapping(value = "/registration")
    public ModelAndView register(@RequestParam HashMap<String, String> allParams, User user){
        if(allParams.containsKey("success")){
            ModelAndView modelAndView = new ModelAndView("success");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public String register(@Valid @ModelAttribute("student")Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/registration";
        }
        else{
            System.out.println(person);
            return "redirect:/registration?success";
        }
    }
}
