package com.example.koifish.controller.admin;

import com.example.koifish.repository.FishRepository;
import com.example.koifish.repository.PondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
    @Autowired
    private FishRepository fishRepository;

    @Autowired
    private PondRepository pondRepository;
    @GetMapping("/home")
    public String home(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        model.addAttribute("countFish", fishRepository.count());
        model.addAttribute("countPonds", pondRepository.count());
        return "admin/dashboard";
    }
}