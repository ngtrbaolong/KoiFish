package com.example.koifish.repository;

import com.example.koifish.model.Fish;
import com.example.koifish.model.FishGrowth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FishGrowthRepository extends JpaRepository<FishGrowth, Long> {
    List<FishGrowth> findAllByFishId(long id);
}
