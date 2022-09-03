package com.alper.carsnotification;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fname;
    private String city;
    private String country;
    private String model;
    private String manufactorer;
    private Integer soldyear;
    @Column(name = "credittype")
    private String credittype;
    private Integer price;
}