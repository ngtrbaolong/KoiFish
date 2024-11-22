package com.example.koifish.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FishGrowth")
public class FishGrowth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;

	private Long age;
	private Float size;
	private Float weight;
	private Float foodAmount;

	@ManyToOne
	@JoinColumn(name = "fish_id")
	private Fish fish;
}