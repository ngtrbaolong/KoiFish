package com.example.koifish.repository;

import com.example.koifish.model.Fish;
import com.example.koifish.model.WaterParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface WaterParamRepository extends JpaRepository<WaterParameter, Long> {

}
