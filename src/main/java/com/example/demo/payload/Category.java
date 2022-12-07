package com.example.demo.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class Category {
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("language")
    private String language;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
