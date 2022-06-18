package com.alper.security.multiauth.security.filters;

import com.alper.security.multiauth.entities.Otp;
import com.alper.security.multiauth.repositories.OtpRepository;
import com.alper.security.multiauth.security.authentications.OtpAuth;
import com.alper.security.multiauth.security.authentications.UsernamePasswordAuth;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserNamePasswordAuthFilter  extends OncePerRequestFilter {


    public UserNamePasswordAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpRepository otpRepository;




    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //1 . Username & password
        //2 . username & otp

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");

        if(otp == null){
            //step-1
            UsernamePasswordAuth passwordAuth = new UsernamePasswordAuth(username,password,null);
            Authentication result = authenticationManager.authenticate(passwordAuth);
            String code = String.valueOf(new Random().nextInt(9999) + 1000);
            //Generate OTP
            Otp generatedOtp = Otp.builder().otp(code).username(username).build();
            otpRepository.save(generatedOtp);
        }else{
            //step-2
            OtpAuth otpAuth = new OtpAuth(username,otp);
            Authentication result = authenticationManager.authenticate(otpAuth);
            //issue a token
            response.setHeader("Authorization", UUID.randomUUID().toString());
        }
    }
}
