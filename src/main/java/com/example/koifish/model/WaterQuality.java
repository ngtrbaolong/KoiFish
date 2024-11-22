package com.example.koifish.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "WaterQuality")
@Data
public class WaterQuality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Float MucdoPh;
    private Float MucdoOxygen;
    private Float Nhietdo;

    @ManyToOne
    @JoinColumn(name = "pond_id")
    private Pond pond;

}
