package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammEntity {
    @Id
    private Long id;
    private String name;
    private String image;
    private String gotolink;
    private String productsXmlLink;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "programm_category",
            joinColumns = {
                    @JoinColumn(name = "program_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id", referencedColumnName = "id")
            }
    )
    @JsonManagedReference
    private List<CategoryEntity> categoryEntityList;


}
