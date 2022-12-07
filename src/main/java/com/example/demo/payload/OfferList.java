package com.example.demo.payload;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@Data
public class OfferList {
    private List<Offer> offer;
}
