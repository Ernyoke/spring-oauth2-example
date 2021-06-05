package dev.esz.oauth2example;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping()
    public String mainPage() {
        return "main.html";
    }

    @GetMapping("/admin")
    public String adminPage(Authentication authentication) {
        System.out.println(authentication.getPrincipal());
        return "admin.html";
    }
}
