package com.alper.security.multiauth.security.filters;

import com.alper.security.multiauth.security.authentications.UsernamePasswordAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserNamePasswordAuthFilter  extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //1 . Username & password
        //2 . username & otp

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");

        if(otp != null){
            //step-1
            UsernamePasswordAuth passwordAuth = new UsernamePasswordAuth(username,password);
            authenticationManager.authenticate(passwordAuth);

        }else{
            //step-2
        }
    }
}
