package fi.haagahelia.kautonen.vinyylisovellus.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    // Näyttää login.html–template:n
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
}
