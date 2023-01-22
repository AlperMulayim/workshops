package com.alper.mongodbtutor.externalapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CarsSqlApi {

    public List<CarDto> getCarData() throws JsonProcessingException {
        String url = "http://localhost:8080/cars";
        RestTemplate restTemplate = new RestTemplate();

        String data =  restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<CarDto> cardata = mapper.readValue(data,new TypeReference<List<CarDto>>(){});

        return  cardata;
    }
}
