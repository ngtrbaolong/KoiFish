package com.example.koifish.controller.admin;


import com.example.koifish.dto.FishDto;
import com.example.koifish.dto.FishGrowthDto;
import com.example.koifish.model.Fish;
import com.example.koifish.model.FishGrowth;
import com.example.koifish.model.Pond;
import com.example.koifish.repository.FishGrowthRepository;
import com.example.koifish.repository.FishRepository;
import com.example.koifish.repository.PondRepository;
import com.example.koifish.repository.StorageService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/fish")
public class AdminFishController {

    private final PondRepository pondRepository;
    private final FishRepository fishRepository;
    private final FishGrowthRepository fishGrowthRepository;
    private final StorageService storageService;
    private final ModelMapper modelMapper;

    public AdminFishController(PondRepository pondRepository, FishRepository fishRepository, FishGrowthRepository fishGrowthRepository, StorageService storageService, ModelMapper modelMapper) {
        this.pondRepository = pondRepository;
        this.fishRepository = fishRepository;
        this.fishGrowthRepository = fishGrowthRepository;
        this.storageService = storageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public String list(Model model) {
        List<Fish> fishes = fishRepository.findAll();
        model.addAttribute("fishes", fishes);
        return "admin/fish/list";
    }
    @GetMapping("/insertPage")
    public String insertPage(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        FishDto fish = new FishDto();
        model.addAttribute("fish", fish);
        model.addAttribute("ponds", pondRepository.findAll());
        return "admin/fish/insert";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "fish") FishDto fishDto,
                       @RequestParam(value = "fileImage", required = false) MultipartFile fileImage) {

        Fish fish = modelMapper.map(fishDto, Fish.class);
        Pond pond = pondRepository.findById(fishDto.getPondId()).get();
        fish.setPond(pond);

        Fish save = fishRepository.save(fish);
        if(!fileImage.isEmpty() && fileImage != null) {
            LocalDateTime ldt = LocalDateTime.now();
            String fileName = "thumb_" + save.getId() + "_" + ldt;
            String saveLink = storageService.uploadFile(fileImage, fileName);
            save.setImage(saveLink);
        }
        fishRepository.save(save);
        return "redirect:/admin/fish";
    }


    @GetMapping("/update/{id}")
    public String getFormUpdate(@PathVariable("id") Long id, Model model){
        model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        Fish fish = fishRepository.findById(id).get();
        FishDto fishDto = modelMapper.map(fish, FishDto.class);
        fishDto.setPondId(fish.getPond().getId());
        fishDto.setPondName(fish.getPond().getName());
        model.addAttribute("fish", fishDto);
        model.addAttribute("id", id);
        model.addAttribute("ponds", pondRepository.findAll());

        return "admin/fish/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "fish") FishDto fishDto,
                         @RequestParam(value = "fileImage", required = false) MultipartFile fileImage) {

        Fish fish = modelMapper.map(fishDto, Fish.class);
        Pond pond = pondRepository.findById(fishDto.getPondId()).get();
        fish.setPond(pond);

        Fish origin = fishRepository.findById(fishDto.getId()).get();
        String image = origin.getImage();


        if(!fileImage.isEmpty() && fileImage != null) {
            LocalDateTime ldt = LocalDateTime.now();
            String fileName = "thumb_" + origin.getId() + "_" + ldt;
            String saveLink = storageService.uploadFile(fileImage, fileName);
            fish.setImage(saveLink);
        } else {
            fish.setImage(image);
        }

        if(!fileImage.isEmpty() && fileImage != null) {
            LocalDateTime ldt = LocalDateTime.now();
            String fileName = "thumb_" + fish.getId() + "_" + ldt;
            String saveLink = storageService.uploadFile(fileImage, fileName);
            fish.setImage(saveLink);
        }
        fishRepository.save(fish);
        return "redirect:/admin/fish/update/" + fishDto.getId();
    }

    @GetMapping("/updateGrowthPage/{id}")
    public String getFormUpdateGrowth(@PathVariable("id") Long id, Model model){
        model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        Fish fish = fishRepository.findById(id).get();

        FishGrowthDto fishGrowth = new FishGrowthDto();
        fishGrowth.setFishId(fish.getId());
        fishGrowth.setFishName(fish.getName());
        model.addAttribute("fishGrowth", fishGrowth);
        model.addAttribute("fishes", fishRepository.findAll());

        model.addAttribute("id", id);
        return "admin/fish/updateGrowth";
    }

    @PostMapping("/updateGrowth")
    public String updateGrowth(@ModelAttribute(name = "fishGrowth") FishGrowthDto fishGrowthDto) {

        FishGrowth fishGrowth = modelMapper.map(fishGrowthDto, FishGrowth.class);

        Fish fish = fishRepository.findById(fishGrowthDto.getFishId()).get();
        fish.setAge(fishGrowth.getAge());
        fish.setSize(fishGrowth.getSize());
        fish.setWeight(fishGrowth.getWeight());
        Fish save = fishRepository.save(fish);
        fishGrowth.setFish(save);
        fishGrowthRepository.save(fishGrowth);
        return "redirect:/admin/fish";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        List<FishGrowth> fishGrowths = fishGrowthRepository.findAllByFishId(id);
        fishGrowthRepository.deleteAll(fishGrowths);
        fishRepository.deleteById(id);
        return "redirect:/admin/fish";
    }
}