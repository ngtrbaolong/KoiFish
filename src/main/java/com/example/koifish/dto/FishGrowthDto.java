package com.example.koifish.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class FishGrowthDto {
    private Long id;
    private Long fishId;
    private String fishName;
    private LocalDate date;
    private Long age;
    private Float size;
    private Float weight;
    private Float foodAmount;
}
