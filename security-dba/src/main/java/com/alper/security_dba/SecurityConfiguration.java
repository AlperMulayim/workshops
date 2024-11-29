package com.alper.security_dba;

import com.alper.security_dba.users.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(registry->{
            registry.requestMatchers("/api/public","/api/users/register/**").permitAll();
            registry.requestMatchers("/api/admin").hasRole("ADMIN");
            registry.requestMatchers("/api/customer").hasRole("CUSTOMER");
            registry.anyRequest().authenticated();
        })
                .csrf().disable()
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails customer = User.builder()
//                .username("customeruser")
//                .password(new BCryptPasswordEncoder().encode("1234"))
//                .roles("CUSTOMER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("adminuser")
//                .password(new BCryptPasswordEncoder().encode("1234"))
//                .roles("ADMIN","CUSTOMER")
//                .build();
//
//        return new InMemoryUserDetailsManager(customer,admin);
//
//    }


    @Bean
    public UserDetailsService userDetailsService(){
        return appUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
