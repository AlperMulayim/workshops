package com.alper.security.multiauth.security.providers;

import com.alper.security.multiauth.security.authentications.UsernamePasswordAuth;
import com.alper.security.multiauth.service.JpaUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UsernamePasswordAuthProvider  implements AuthenticationProvider {


    private JpaUserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password =(String) authentication.getCredentials();
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(password,user.getPassword())){
            return new UsernamePasswordAuth(username,password, List.of(()-> "read"));
        }
        throw  new BadCredentialsException("UsernamePasswordAuthProvider not validate passwords and usernames");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuth.class.equals(authentication);
    }
}
