package com.example.koifish.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Fish")
public class Fish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String image;
	private String bodyShape;
	private Long age;
	private Float size;
	private Float weight;
	private Boolean gender;
	private String breed;
	private String origin;
	private Float price;
	@ManyToOne
	@JoinColumn(name = "pond_id")
	private Pond pond;
}