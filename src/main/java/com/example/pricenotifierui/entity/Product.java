package com.example.pricenotifierui.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Task task;
    private String name;
    private String productExtId;
    private String companyExtId;
    private String companyExtName;
    private String price;
    private String currency;
    private String unit;
    private String url;
    private String domain;
}
