package com.example.koifish.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "WaterParameter")
@Data
public class WaterParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "measurement_date")
    private Date measurementDate;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "salinity")
    private Double salinity;

    @Column(name = "ph")
    private Double ph;

    @Column(name = "o2")
    private Double o2;

    @Column(name = "no2")
    private Double no2;

    @Column(name = "no3")
    private Double no3;

    @Column(name = "po4")
    private Double po4;

    @ManyToOne
    @JoinColumn(name = "pond_id")
    private Pond pond;

}
