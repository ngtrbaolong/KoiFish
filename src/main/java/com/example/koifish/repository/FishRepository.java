package com.example.koifish.repository;

import com.example.koifish.model.Fish;
import com.example.koifish.model.Pond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FishRepository extends JpaRepository<Fish, Long> {

}
