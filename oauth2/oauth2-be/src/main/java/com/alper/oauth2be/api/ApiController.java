package com.alper.oauth2be.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mydata")
public class ApiController {

    @GetMapping()
    public String mydata(){
        return "Hello World";
    }
}
