package ba.sum.fsre.ednevnik1.controllers;

import ba.sum.fsre.ednevnik1.models.user;
import ba.sum.fsre.ednevnik1.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String listUsers (Model model){
        List<user> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users/index";
    }

    // U klasi UserController

    @GetMapping("/users/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new user());
        return "users/add";
    }

    @PostMapping("/users/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addUser(@Valid user user, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("user", user);
            return "users/add";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordEncoded = encoder.encode(user.getLozinka());
            user.setLozinka(passwordEncoded);
            user.setPotvrdaLozinke(passwordEncoded);
            userRepository.save(user);
            return "redirect:/users";
        }
    }

    // U klasi UserController

    @PostMapping("/users/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable Long userId) {
        userRepository.deleteById(userId);
        return "redirect:/users";
    }

    // U klasi UserController

    @GetMapping("/users/edit/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showEditUserForm(@PathVariable Long userId, Model model) {
        user user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID korisnika: " + userId));
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/users/edit/{userId}")
    public String updateUser(@PathVariable Long userId, @ModelAttribute user user, Model model) {
        // Provjerite postoji li korisnik s tim ID-om
        user existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID korisnika: " + userId));
        existingUser.setIme(user.getIme());
        existingUser.setPrezime(user.getPrezime());
        existingUser.setEmail(user.getEmail());
        // Lozinka
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String lozinka = encoder.encode(user.getLozinka());
        existingUser.setLozinka(lozinka);
        existingUser.setPotvrdaLozinke(lozinka);
        existingUser.setRoles(user.getRoles());
        // Postavite ostala polja po potrebi
        userRepository.save(existingUser);
        return "redirect:/users";
    }
}