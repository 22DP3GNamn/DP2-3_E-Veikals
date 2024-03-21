package rvt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "/login")
    String Login(){
        return "login";
    }

    @GetMapping(value = "/shoppin")
    String shoppin(){
        return "shoppin";
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

    @GetMapping(value = "/successRegister")
    public String success() {
        return "login";
    }

    @GetMapping(value = "/registration")
    public ModelAndView register(@RequestParam(name="name", required=false, defaultValue="null") String name, Model model) {
        ModelAndView modelAndView = new ModelAndView("registration");
        return modelAndView;
    }







//     @GetMapping(value = "/registration")
//     public ModelAndView register(@RequestParam HashMap<String, String> allParams, User user){
//         if(allParams.containsKey("success")){
//             ModelAndView modelAndView = new ModelAndView("success");
//             return modelAndView;
//         }
//         ModelAndView modelAndView = new ModelAndView("registration");
//         return modelAndView;
//     }

//     @PostMapping(value = "/registration")
//     public String register(@Valid @ModelAttribute("student")Person person, BindingResult bindingResult){
//         if(bindingResult.hasErrors()){
//             return "/registration";
//         }
//         else{
//             System.out.println(person);
//             return "redirect:/success";
//         }
//     }
}
