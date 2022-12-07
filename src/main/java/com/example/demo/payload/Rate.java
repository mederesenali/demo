package com.example.demo.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Rate {
    @JsonProperty("price_s")
    private String priceS;
    @JsonProperty("size")
    private String size;
    @JsonProperty("tariff_id")
    private Integer tariffId;
    @JsonProperty("country")
    private Object country;
    @JsonProperty("date_s")
    private String dateS;
    @JsonProperty("is_percentage")
    private Boolean isPercentage;
    @JsonProperty("id")
    private Integer id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
