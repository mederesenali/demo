package com.example.demo.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ActionDetail {
    @JsonProperty("tariffs")
    private List<Tariff> tariffs = new ArrayList<>();
    @JsonProperty("type")
    private String type;
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("hold_size")
    private Integer holdSize;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
