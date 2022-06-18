package com.alper.security.multiauth.config;

import com.alper.security.multiauth.entities.User;
import com.alper.security.multiauth.security.authentications.UsernamePasswordAuth;
import com.alper.security.multiauth.security.filters.UserNamePasswordAuthFilter;
import com.alper.security.multiauth.security.providers.OtpAuthProvider;
import com.alper.security.multiauth.security.providers.UsernamePasswordAuthProvider;
import com.alper.security.multiauth.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Bean
    public JpaUserDetailsService userDetailsService(){
        return new JpaUserDetailsService();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  NoOpPasswordEncoder.getInstance();
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    private UsernamePasswordAuthProvider authProvider;

    @Autowired
    private OtpAuthProvider otpAuthProvider;

    @Autowired
    private UserNamePasswordAuthFilter authFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        authProvider = new UsernamePasswordAuthProvider(userDetailsService(),passwordEncoder());
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(usernamePasswordAuthFilter(),BasicAuthenticationFilter.class);
    }

    @Bean
    public UserNamePasswordAuthFilter usernamePasswordAuthFilter() throws Exception {
        return new UserNamePasswordAuthFilter(authenticationManagerBean());
    }
}
