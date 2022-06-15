- Create new User Details for Spring Security 
- Create new user details  SERVICE for security
- Create new security config with new user details service bean


``````java
package com.alper.leasesoftprobe.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LesaUser  implements UserDetails {

    private String uname;
    private String pass;
    private  String authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> authority);
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return uname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
``````


```````java
@AllArgsConstructor
public class LeaseSoftInMemUserDetailsService  implements UserDetailsService {

    private List<UserDetails> userDetailsList;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return  userDetailsList.stream().filter(userDetails -> userDetails.getUsername().equals(username)).findFirst().get();
    }
}

````````


`````````java
@Configuration
public class LeaseSoftSecurityConfInMemory extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){
        LesaUser u1 = LesaUser.builder().uname("alperm").pass("1234").authority("read").build();
        LesaUser u2 = LesaUser.builder().uname("alperm2").pass("1235").authority("read").build();

        return new LeaseSoftInMemUserDetailsService(List.of(u1,u2));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
``````````


