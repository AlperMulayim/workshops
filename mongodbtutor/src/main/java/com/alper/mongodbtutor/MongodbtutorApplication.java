package com.alper.mongodbtutor;

import com.alper.mongodbtutor.cars.Car;
import com.alper.mongodbtutor.externalapi.CarDto;
import com.alper.mongodbtutor.cars.CarsRepository;
import com.alper.mongodbtutor.externalapi.CarsSqlApi;
import com.alper.mongodbtutor.mapper.CarMapper;
import com.alper.mongodbtutor.mapper.EntityDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableMongoRepositories
public class MongodbtutorApplication {

	@Autowired
	CarsSqlApi carsSqlApi;

	@Autowired
	CarsRepository carsRepository;

	public static void main(String[] args) {
		SpringApplication.run(MongodbtutorApplication.class, args);
	}

	@Bean
	public void getCarsFromExternalApi() throws JsonProcessingException {

		if(carsRepository.count() == 0){
			List<CarDto> cars = carsSqlApi.getCarData();

			EntityDtoMapper<Car,CarDto> mapper = new CarMapper();

			List<Car> carEntities = cars.stream()
					.map(carDto -> mapper.toEntity(carDto) )
					.collect(Collectors.toList());
			carsRepository.saveAll(carEntities);

			System.out.println("Cars imported from SQL Spring Cars added to MongoDB cars");
		}
		System.out.println("Using MongoDB cars");
	}
}

