package com.alper.security_dba.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService  implements UserDetailsService {
    @Autowired
    private AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser =  repository.findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("Username could not found"));

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(getRoles(appUser))
                .build();
    }

    private String[] getRoles(AppUser user){
        if(user.getRole() == null){
            return new String[]{"PUBLIC"};
        }
        return user.getRole().split(",");
    }
}
