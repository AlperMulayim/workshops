package com.alper.security_dba;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/api/public")
    public String publicAPI(){
        return "PUBLIC API is working!";
    }
    @GetMapping("/api/admin")
    public String adminAPI(){
        return "ADMIN API is working!";
    }
    @GetMapping("/api/customer")
    public String customerAPI(){
        return "CUSTOMER API is working!";
    }
}
