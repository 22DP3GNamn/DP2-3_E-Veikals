package rvt;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import lv.rvt.CheckoutForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ui.Model;
import java.util.*;

@Controller
@SessionAttributes("cart")

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
        List<Person> persons = CSVManager.getAllUsers();
        model.addAttribute("persons", persons);
        return "allUserManager";
    }

    @PostMapping(value="/deleteUser")
    public String deleteUser(@RequestParam("email") String email, HttpServletRequest request, RedirectAttributes redirectAttributes) throws CsvValidationException {
        System.out.println("Email to delete: " + email);
        boolean result = CSVManager.deletePerson(email);
        if (result) {
            return "redirect:/allUserManager";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User could not be deleted.");
            return "redirect:/allUserManager";
            }
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
            bindingResult.rejectValue("email", "error.email", "E-mail is registered!");
            return "/registration";
        }
        if(!person.getPassword().equals(person.getConfirmPassword())){
            bindingResult.rejectValue("password", "error.confirmPassword", "Password doesn't match!");
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
            bindingResult.rejectValue("email", "error.email", "E-email not registered!");
            return "/login";
        }
        if(!CSVManager.login(person.getEmail(), person.getPassword())){
            bindingResult.rejectValue("password", "error.password", "Wrong password!");
            return "/login";
        }
        if (CSVManager.login(person.getEmail(), person.getPassword())){
            Person fullPerson = CSVManager.getPersonByEmail(person.getEmail());
            request.getSession().setAttribute("person", fullPerson);

            Cart cart = new Cart();
            request.getSession().setAttribute("cart", cart);

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
    public String changeEmail(@RequestParam("newEmail") String newEmail, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Person person = (Person) request.getSession().getAttribute("person");
        if (person != null) {
            if(Validator.validateEmail(newEmail)){
                CSVManager.changeEmail(person.getEmail(), newEmail, request.getSession());
                person.setEmail(newEmail);
                request.getSession().setAttribute("person", person);
            } else {
                redirectAttributes.addFlashAttribute("emailError", "Invalid email format.");
            }
        }
        return "redirect:/profile";
    }

    @PostMapping(value="/changePassword")
    public String changePassword(@RequestParam("newPassword") String newPassword, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Person person = (Person) request.getSession().getAttribute("person");
        if (person != null) {
            if(Validator.validatePassword(newPassword)){
                CSVManager.changePassword(person.getEmail(), person.getPassword(), newPassword, request.getSession());
                person.setPassword(newPassword);
                request.getSession().setAttribute("person", person);
            } else {
                redirectAttributes.addFlashAttribute("passwordError", "Invalid password format.");
            }
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

    @PostMapping(value="/shoppin/sort")
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
        return "/shoppin";
    }

    @PostMapping("/shoppin/filter")
    public String filterProducts(@RequestParam String filter, Model model) {
        List<Product> products = CSVManager.filterProducts(filter);
        model.addAttribute("products", products);
        return "/shoppin";
    }

    @GetMapping("/Checkout")
    public String checkout() {
        return "/checkout";
    }
    
    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("checkoutForm") CheckoutForm form, BindingResult bindingResult, Model model, HttpServletRequest request){
        Person person = (Person) request.getSession().getAttribute("person");
        boolean loggedIn = person != null;
        model.addAttribute("loggedIn", loggedIn);

        if (loggedIn) {
            if (person != null) {
                form.setName(person.getName());
                form.setSurname(person.getSurname());
                form.setEmail(person.getEmail());
            }
        }else if (!Validator.validateName(form.getName())) {
            model.addAttribute("nameError", "Invalid name");
            return "/checkout";
        }else if(!Validator.validateSurname(form.getSurname())) {
            model.addAttribute("surnameError", "Invalid surname");
            return "/checkout";
        }else if(!Validator.validateEmail(form.getEmail())) {
            model.addAttribute("emailError", "Invalid email");
            return "/checkout";
        }

        boolean hasErrors = false;

        if(!Validator.validateAddress(form.getAddress())) {
            model.addAttribute("addressError", "Invalid address");
            hasErrors = true;
        }

        if(!Validator.validateCard(form.getCard())) {
            model.addAttribute("cardError", "Invalid card");
            hasErrors = true;
        }

        if(!Validator.validateExpirityDate(form.getExpiryDate())) {
            model.addAttribute("expiryError", "Invalid expiry date");
            hasErrors = true;
        }

        if(!Validator.validateCvv(form.getCvv())) {
            model.addAttribute("cvvError", "Invalid CVV");
            hasErrors = true;
        }

        if (hasErrors) {
            return "/checkout";
        }

        CSVManager.writeCheckoutData(form);
        return "redirect:/";
    }
    
    // CART
    @Autowired
    private HttpSession httpSession;

    @GetMapping(value = "/YourCart")
    public String YourCart() {
        return "YourCart";
    }

    @ModelAttribute("cart")
    public Cart getCart() {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
        }
        return cart;
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String productId, HttpSession session) {
        int id = Integer.parseInt(productId);
        Product product = CSVManager.getProductById(id);  
        Cart cart = (Cart) session.getAttribute("cart");
        cart.addProduct(product);
        session.setAttribute("cart", cart);
        return "redirect:/shoppin";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "redirect:/YourCart";
    }

    @PostMapping("/delete-product")
    public String deleteProduct(@RequestParam String productId, HttpSession session) {
        int id = Integer.parseInt(productId);
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            List<Product> items = cart.getItems();
            items.removeIf(product -> product.getId() == id);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }
    

}