package com.example.demo.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Action {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("payment_size")
    private String paymentSize;
    @JsonProperty("hold_time")
    private Integer holdTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
