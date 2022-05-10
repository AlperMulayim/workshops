package com.alper.mockitoworkshop.veichles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Veichle {
    private String vin;
    private String make;
    private String model;
    private Integer year;
}
