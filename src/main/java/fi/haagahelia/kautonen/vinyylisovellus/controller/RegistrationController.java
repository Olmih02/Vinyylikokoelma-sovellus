package fi.haagahelia.kautonen.vinyylisovellus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;

import fi.haagahelia.kautonen.vinyylisovellus.domain.AppUser;
import fi.haagahelia.kautonen.vinyylisovellus.repository.AppUserRepository;

@Controller
public class RegistrationController {

    @Autowired
    private AppUserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;  // injektoi WebSecurityConfigista

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("appUser", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute AppUser appUser) {
        // salasanan koodaus
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        // oletusrooli
        appUser.setRole("USER");
        userRepo.save(appUser);
        return "redirect:/login?registered";
    }
}
