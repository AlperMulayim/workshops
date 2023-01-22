package com.alper.mongodbtutor.cars;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cars")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car {
    @Id
    private ObjectId objectId;
    @Indexed
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