package com.example.koifish.dto;


import lombok.Data;

@Data
public class FishDto {
    private Long id;
    private Long pondId;
    private String name;
    private String pondName;
    private String image;
    private String bodyShape;
    private Long age;
    private Float size;
    private Float weight;
    private Boolean gender;
    private String breed;
    private String origin;
    private Float price;
}
