package com.example.demo.repository;

import com.example.demo.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<ProductEntity,Long> {
    Page<ProductEntity> findByNameAndBrand(String name,String brand, Pageable pageable);
}
