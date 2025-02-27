package ba.sum.fsre.ednevnik1.controllers;
import ba.sum.fsre.ednevnik1.models.user;
import ba.sum.fsre.ednevnik1.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class AuthController {
    @Autowired
    UserRepository userRepo;
    @GetMapping("/auth/register")
    public String add (Model model){
        user user = new user();
        model.addAttribute("user", user);
        return "users/register";
    }
    @PostMapping("/auth/register")
    public String newUser (@Valid user user, BindingResult result, Model model){
        boolean errors = result.hasErrors();
        if (errors){
            model.addAttribute("user", user);
            return "users/register";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordEncoded = encoder.encode(user.getLozinka());
            user.setLozinka(passwordEncoded);
            user.setPotvrdaLozinke(passwordEncoded);
            userRepo.save(user);
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/auth/login")
    public String login (Model model){
        model.addAttribute("user", new user());
        return "users/login";
    }
}