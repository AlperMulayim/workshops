package com.alper.mongodbtutor.cars;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarsRepository extends MongoRepository<Car,String> {
}
