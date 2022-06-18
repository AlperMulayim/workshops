package com.security.alper.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;

@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping
    public String HELLO(){
        return "Hello";
    }
}
