package com.alper.streamcars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
//create table cars (
//        id INT,
//        fname VARCHAR(50),
//        city VARCHAR(50),
//        country VARCHAR(50),
//        model VARCHAR(50),
//        manufactorer VARCHAR(50),
//        soldyear INT,
//        credittype VARCHAR(50),
//        price INT
//        );

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
