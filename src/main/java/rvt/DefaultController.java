package rvt;

import java.util.HashMap;

// import javax.naming.Binding;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

// import jakarta.servlet.http.HttpServletRequest;
// import javax.validation.Valid;

@Controller
public class DefaultController {
    
<<<<<<< HEAD
    @GetMapping(value = "/")
    ModelAndView index(@RequestParam(name="name", required=false, defaultValue="null") String name) {
        Person person = new Person("John", "Smith", "a@a.lv", "DP2-4");
        List<Person> persona = new ArrayList<>();
        persona.add(person);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("persona", persona);
        modelAndView.addObject("date", new Date().toString());
=======
   @GetMapping(value = "/")
    String index(@RequestParam(name="name", required=false, defaultValue="null") String name, Model model) {
        return "index";
    }

    @GetMapping(value = "/hello")
    String hello(){
        return "hello";
    } 

    @GetMapping(value = "/about")
    ModelAndView about(){
        ModelAndView modelAndView = new ModelAndView("about");
    return modelAndView;
    }

    @GetMapping(value = "/Links")
    String Links(){
        return "Links";
    }

    @GetMapping(value = "/Experiment")
    String Experiment(){
        return "Experiment";
    }

    @GetMapping(value = "/test")
    public ModelAndView testAction(){
        ModelAndView modelAndView = new ModelAndView("test");
>>>>>>> 2f757cb (commits)
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

    // @PostMapping(value = "/registration")
    // public String register(@Valid @ModelAttribute("student")Person person, BindingResult bindingResult){
    //     if(bindingResult.hasErrors()){
    //         return "/registration";
    //     }
    //     else{
    //         System.out.println(person);
    //         return "redirect:/registration?success";
    //     }
    // }
}