package com.alper.websockettutor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
public class MyController {

    @GetMapping()
    public String myController(){
        return "Hello API";
    }
}
