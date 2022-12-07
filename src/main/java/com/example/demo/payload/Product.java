package com.example.demo.payload;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "shop")
@Data
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Product {
    OfferList offers;

}
