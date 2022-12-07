package com.example.demo.payload;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "yml_catalog")
@Data
@XmlAccessorType(XmlAccessType.PROPERTY)
public class YmlCatalog {
    private Product shop;
    public Date date;
    public String text;
}
