package com.example.koifish.controller.admin;

import com.example.koifish.model.Product;
import com.example.koifish.repository.ProductRepository;
import com.example.koifish.repository.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    private final StorageService storageService;
    @Autowired
    private ProductRepository productRepository;

    public AdminProductController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("")
    public String list(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin/product/list";
    }
    @GetMapping("/insertPage")
    public String insertPage(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        Product product = new Product();
        model.addAttribute("product", product);
        return "admin/product/insert";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "product") Product product,
                       @RequestParam(value = "fileImage", required = false) MultipartFile fileImage) {

        Product save = productRepository.save(product);
        if(!fileImage.isEmpty() && fileImage != null) {
            LocalDateTime ldt = LocalDateTime.now();
            String fileName = "thumb_" + save.getId() + "_" + ldt;
            String saveLink = storageService.uploadFile(fileImage, fileName);
            save.setImg(saveLink);
        }
        productRepository.save(save);
        return "redirect:/admin/product";
    }


    @GetMapping("/update/{id}")
    public String getFormUpdate(@PathVariable("id") Long id, Model model){
        model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("id", product.getId());
        return "admin/product/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "product") Product product,
                         @RequestParam(value = "fileImage", required = false) MultipartFile fileImage) {

        Product origin = productRepository.findById(product.getId()).get();


        String image = origin.getImg();
        Product save = productRepository.save(product);

        if(!fileImage.isEmpty() && fileImage != null) {
            LocalDateTime ldt = LocalDateTime.now();
            String fileName = "thumb_" + save.getId() + "_" + ldt;
            String saveLink = storageService.uploadFile(fileImage, fileName);
            save.setImg(saveLink);
        } else {
            save.setImg(image);
        }

        if(!fileImage.isEmpty() && fileImage != null) {
            LocalDateTime ldt = LocalDateTime.now();
            String fileName = "thumb_" + save.getId() + "_" + ldt;
            String saveLink = storageService.uploadFile(fileImage, fileName);
            save.setImg(saveLink);
        }
        productRepository.save(save);
        return "redirect:/admin/product/update/" + product.getId();
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        productRepository.deleteById(id);
        return "redirect:/admin/product";
    }
}