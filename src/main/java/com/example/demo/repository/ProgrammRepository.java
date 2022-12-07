package com.example.demo.repository;

import com.example.demo.entity.ProgrammEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammRepository extends JpaRepository<ProgrammEntity,Long> {
}
