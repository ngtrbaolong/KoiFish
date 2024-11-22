package com.example.koifish.controller.admin;


import com.example.koifish.dto.FishDto;
import com.example.koifish.dto.FishGrowthDto;
import com.example.koifish.dto.WaterParameterDto;
import com.example.koifish.model.Fish;
import com.example.koifish.model.FishGrowth;
import com.example.koifish.model.Pond;
import com.example.koifish.model.WaterParameter;
import com.example.koifish.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/waterParam")
public class AdminWaterParamController {

    private final PondRepository pondRepository;
    private final FishRepository fishRepository;
    private final FishGrowthRepository fishGrowthRepository;
    private final StorageService storageService;
    private final ModelMapper modelMapper;
    @Autowired
    private WaterParamRepository waterParamRepository;

    public AdminWaterParamController(PondRepository pondRepository, FishRepository fishRepository, FishGrowthRepository fishGrowthRepository, StorageService storageService, ModelMapper modelMapper) {
        this.pondRepository = pondRepository;
        this.fishRepository = fishRepository;
        this.fishGrowthRepository = fishGrowthRepository;
        this.storageService = storageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public String list(Model model) {
        List<WaterParameter> waterParams = waterParamRepository.findAll();
        model.addAttribute("waterParams", waterParams);
        return "admin/waterParam/list";
    }
    @GetMapping("/insertPage")
    public String insertPage(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        WaterParameterDto waterParam = new WaterParameterDto();
        model.addAttribute("waterParam", waterParam);
        model.addAttribute("ponds", pondRepository.findAll());
        return "admin/waterParam/insert";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "waterParam") WaterParameterDto waterParam) {
        WaterParameter waterParameter = modelMapper.map(waterParam, WaterParameter.class);
        Pond pond = pondRepository.findById(waterParam.getPondId()).get();
        waterParameter.setPond(pond);
        waterParamRepository.save(waterParameter);
        return "redirect:/admin/waterParam";
    }


    @GetMapping("/updatePage/{id}")
    public String getFormUpdate(@PathVariable("id") Long id, Model model){
        model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        WaterParameter waterParameter = waterParamRepository.findById(id).get();
        WaterParameterDto waterParameterDto = modelMapper.map(waterParameter, WaterParameterDto.class);
        waterParameterDto.setPondId(waterParameter.getPond().getId());
        waterParameterDto.setPondName(waterParameter.getPond().getName());
        model.addAttribute("waterParam", waterParameterDto);
        model.addAttribute("id", id);
        model.addAttribute("ponds", pondRepository.findAll());
        return "admin/waterParam/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "fish") WaterParameterDto waterParam) {
        WaterParameter waterParameter = modelMapper.map(waterParam, WaterParameter.class);
        Pond pond = pondRepository.findById(waterParam.getPondId()).get();
        waterParameter.setPond(pond);
        waterParamRepository.save(waterParameter);
        return "redirect:/admin/waterParam";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        waterParamRepository.deleteById(id);
        return "redirect:/admin/waterParam";
    }
}