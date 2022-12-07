package com.example.demo.payload;

import lombok.Getter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Getter
public class Offer {
    public int age;
    public int categoryId;
    public String currencyId;
    public String description;
    public boolean manufacturer_warranty;
    public int modified_time;
    public String name;
    public String picture;
    public double price;
    public String url;
    public String vendor;
    public boolean available;
    public String text;
}
