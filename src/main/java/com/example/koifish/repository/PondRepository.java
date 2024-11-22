package com.example.koifish.repository;

import com.example.koifish.model.Pond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository

public interface PondRepository extends JpaRepository<Pond, Long> {

}
