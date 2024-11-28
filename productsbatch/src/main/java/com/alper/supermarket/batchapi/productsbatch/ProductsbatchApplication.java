package com.alper.supermarket.batchapi.productsbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ProductsbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsbatchApplication.class, args);
	}

}
