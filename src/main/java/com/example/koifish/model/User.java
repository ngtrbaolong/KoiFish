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
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(unique = true, nullable = true)
	private String username;

	private String email;
	private String fullName;
	private String password;
	private Boolean role;
	private String profilePicture;

	@Column(name="createdAt")
	protected LocalDateTime createdAt;

	private Boolean status;

}
