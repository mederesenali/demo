package com.example.demo.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Programm {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image")
    private String image;
    @JsonProperty("categories")
    private List<Category> categories = new ArrayList<>();
    @JsonProperty("gotolink")
    private String gotolink;
    @JsonProperty("products_xml_link")
    private String productsXmlLink;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
