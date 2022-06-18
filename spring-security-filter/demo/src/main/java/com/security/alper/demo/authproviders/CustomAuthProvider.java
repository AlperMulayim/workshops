package com.security.alper.demo.authproviders;

import com.security.alper.demo.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Value("${key}")
    private String key;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String requestKy = authentication.getName();

       if(requestKy.equals(key)){
           CustomAuthentication customAuthentication = new CustomAuthentication(null,null,null);
           return customAuthentication;

       }else {
           throw  new BadCredentialsException("No Validation Auth");
       }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
