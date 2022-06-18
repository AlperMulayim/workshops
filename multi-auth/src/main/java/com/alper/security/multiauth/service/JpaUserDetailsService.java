package com.alper.security.multiauth.service;

import com.alper.security.multiauth.entities.User;
import com.alper.security.multiauth.repositories.UserRepository;
import com.alper.security.multiauth.security.models.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class JpaUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("not found user"));
        return  new SecurityUser(user);
    }
}
