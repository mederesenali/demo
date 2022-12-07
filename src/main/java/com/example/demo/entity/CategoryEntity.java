package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    private Long id;
    private String name;
    private String language;
    @ManyToMany(mappedBy = "categoryEntityList",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<ProgrammEntity> programmEntityList;


}
