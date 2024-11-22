package com.example.koifish.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pond")
public class Pond {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String image;
	private Float size;
	private Float depth;
	private Float volume;
	private Long drainCount;
	private Float pumpCapacity;


	@OneToMany(mappedBy = "pond", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<WaterParameter> waterParameters = new HashSet<>();

	@OneToMany(mappedBy = "pond", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<WaterQuality> waterQualities = new HashSet<>();

	@OneToMany(mappedBy = "pond", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Fish> fishSet = new HashSet<>();
}