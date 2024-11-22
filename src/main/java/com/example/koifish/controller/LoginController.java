package com.example.koifish.controller;

import com.example.koifish.repository.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        boolean isValidCredentials = userService.validateCredentials(username, password);
        if (isValidCredentials) {
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "auth/login";
        }
    }
}

