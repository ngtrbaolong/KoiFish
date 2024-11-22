package com.example.koifish.repository;

import com.example.koifish.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailAndStatus(String email, Boolean status);
	Optional<User> findByUsernameAndStatus(String username, Boolean status);

	Optional<User> findById(Long id);


	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
	void updatePassword(@Param("password") String password, @Param("email") String email);

	boolean existsByUsernameAndPassword(String username, String password);

	@Query("SELECT COUNT(u) FROM User u")
	long countTotalUsers();



}
