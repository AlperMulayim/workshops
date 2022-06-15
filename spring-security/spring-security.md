`````java


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryConfig extends WebSecurityConfigurerAdapter {
    //UserDetailService - InMemory,
    //Create User for in memory
    //PasswordEncode noop - there is not password hashing

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager udm = new InMemoryUserDetailsManager();
        UserDetails user = User.withUsername("alper").password("alp").authorities("read").build();
        udm.createUser(user);
        return udm;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http basic and
        //services endpoint is public and
        //other endpoints authenticated.
        http.httpBasic()
                .and()
                .authorizeRequests().antMatchers("/api/v1/services").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }
}

`````
