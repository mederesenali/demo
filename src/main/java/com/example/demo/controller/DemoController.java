package com.example.demo.controller;

import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<ProductEntity> productEntityPage;
        if (name == null && brand == null)
            productEntityPage = productRepository.findAll(paging);
        else
            productEntityPage = productRepository.findByNameAndBrand(name, brand, paging);
        List<ProductEntity> productEntityList = productEntityPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("products", productEntityList);
        response.put("currentPage", productEntityPage.getNumber());
        response.put("totalItems", productEntityPage.getTotalElements());
        response.put("totalPages", productEntityPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}