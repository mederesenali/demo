package com.example.demo.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Results {
    @JsonProperty("results")
    private List<Programm> results;
}
