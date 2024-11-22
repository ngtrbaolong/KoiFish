package com.example.koifish.repository;

import com.example.koifish.model.Pond;
import com.example.koifish.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {

}
