package com.alper.mongodbtutor.externalapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarDto {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Integer id;
    private String fname;
    private String city;
    private String country;
    private String model;
    private String manufactorer;
    private Integer soldyear;
    private String credittype;
    private Integer price;
}