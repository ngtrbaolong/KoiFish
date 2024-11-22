package com.example.koifish.controller.admin;


import com.example.koifish.model.Pond;
import com.example.koifish.repository.PondRepository;
import com.example.koifish.repository.StorageService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/pond")
public class AdminPondController {

    private final PondRepository pondRepository;
    private final StorageService storageService;

    public AdminPondController(PondRepository pondRepository, StorageService storageService) {
        this.pondRepository = pondRepository;
        this.storageService = storageService;
    }

    @GetMapping("")
    public String list(Model model) {
        List<Pond> ponds = pondRepository.findAll();
        model.addAttribute("ponds", ponds);
        return "admin/pond/list";
    }
    @GetMapping("/insertPage")
    public String insertPage(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        Pond pond = new Pond();
        model.addAttribute("pond", pond);
        return "admin/pond/insert";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "pond") Pond pond,
                       @RequestParam(value = "fileImage", required = false) MultipartFile fileImage) {

        Pond save = pondRepository.save(pond);
        if(!fileImage.isEmpty() && fileImage != null) {
            LocalDateTime ldt = LocalDateTime.now();
            String fileName = "thumb_" + save.getId() + "_" + ldt;
            String saveLink = storageService.uploadFile(fileImage, fileName);
            save.setImage(saveLink);
        }
        pondRepository.save(save);
        return "redirect:/admin/pond";
    }


    @GetMapping("/update/{id}")
    public String getFormUpdate(@PathVariable("id") Long id, Model model){
        model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        Pond pond = pondRepository.findById(id).get();
        model.addAttribute("pond", pond);
        model.addAttribute("id", pond.getId());
        return "admin/pond/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "pond") Pond pond,
                         @RequestParam(value = "fileImage", required = false) MultipartFile fileImage) {

        Pond origin = pondRepository.findById(pond.getId()).get();


        String image = origin.getImage();
        Pond save = pondRepository.save(pond);

        if(!fileImage.isEmpty() && fileImage != null) {
            LocalDateTime ldt = LocalDateTime.now();
            String fileName = "thumb_" + save.getId() + "_" + ldt;
            String saveLink = storageService.uploadFile(fileImage, fileName);
            save.setImage(saveLink);
        } else {
            save.setImage(image);
        }

        if(!fileImage.isEmpty() && fileImage != null) {
            LocalDateTime ldt = LocalDateTime.now();
            String fileName = "thumb_" + save.getId() + "_" + ldt;
            String saveLink = storageService.uploadFile(fileImage, fileName);
            save.setImage(saveLink);
        }
        pondRepository.save(save);
        return "redirect:/admin/pond/update/" + pond.getId();
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        pondRepository.deleteById(id);
        return "redirect:/admin/pond";
    }
}