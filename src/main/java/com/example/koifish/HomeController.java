package com.example.koifish;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.List;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        session.setAttribute("islogin", false);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        System.out.println("Authenticated username: " + username);
        authorities.forEach(auth -> System.out.println("Authority: " + auth.getAuthority()));

        if (authorities.stream().anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()))) {
            model.addAttribute("email", username);
            return "redirect:/admin/home";
        } else if (authorities.stream().anyMatch(authority -> "ROLE_CLIENT".equals(authority.getAuthority()))) {
            model.addAttribute("email", username);
            return "redirect:/client/home";
        } else {
            return "guest/index";
        }
    }


    @GetMapping("/home")
    public String homePage(Model model, HttpSession session) {
        session.setAttribute("islogin", false);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (authorities.stream().anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()))) {
            model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
            return "redirect:/admin/home";
        } else if (authorities.stream().anyMatch(authority -> "ROLE_CLIENT".equals(authority.getAuthority()))) {
            model.addAttribute("email", username);
            return "redirect:/client/home";
        } else {
            return "guest/index";
        }
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        session.setAttribute("islogin", false);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (authorities.stream().anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()))) {
            model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
            return "redirect:/admin/home";
        } else if (authorities.stream().anyMatch(authority -> "ROLE_CLIENT".equals(authority.getAuthority()))) {
            model.addAttribute("email", username);
            return "redirect:/client/home";
        } else {
            return "guest/index";
        }
    }
}
