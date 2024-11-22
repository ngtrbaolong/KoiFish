package com.example.koifish.dto;

import com.example.koifish.model.Pond;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class WaterParameterDto {
    private Long id;
    private Date measurementDate;
    private Double temperature;
    private Double salinity;
    private Double ph;
    private Double o2;
    private Double no2;
    private Double no3;
    private Double po4;
    private Long pondId;
    private String pondName;
}
