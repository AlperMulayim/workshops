# Spring Security Database DaoAuthenticationProvider

1. Create users, AppUser add roles for users. 
2. Create Repository for AppUser. 
3. Create custom UserServiceDetails extends UserDetailsService
4. Configuration file SecurityConfiguration
    1. Create bean for user details service.  (`UserDetailsService)`
    2. Create bean for authentication provider ( `DaoAuthenticationProvider`)
    3. Create bean password encode. ( `BCryptPasswordEncoder`)
    4. `@Configuration @EnableWebSecurity` for configuration file, enabling security. 
5. Create bean for SecurityFilterChain. 

```java
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(registry->{
                    registry.requestMatchers("/api/public","/api/users/register/**","/swagger-ui/**","/v3/api-docs/**", "/swagger-ui.html").permitAll();
                    registry.requestMatchers("/h2-console/**")
                    .hasRole(AppRoles.SYSADMIN.get());
                    registry.requestMatchers("/api/admin")
                    .hasRole(AppRoles.EMPLOYEE.get());
                    registry.requestMatchers("/api/customer")
                    .hasRole(AppRoles.CUSTOMER.get());
                    registry.requestMatchers("/api/v1/customers/**")
                    .hasRole(AppRoles.EMPLOYEE.get());
                    registry.anyRequest().authenticated();
                })
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .httpBasic(Customizer.withDefaults()) //password and username 
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
```

```java

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
```

```java
public enum AppRoles {
    CUSTOMER("CUSTOMER"),
    EMPLOYEE("EMPLOYEE"),
    SYSADMIN("SYSADMIN");
    private final String name;

    AppRoles(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}
```

```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "app_user",
        uniqueConstraints = @UniqueConstraint(columnNames = "username")
)
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;
}
```

```java
@Service
public class AppUserDetailsService  implements UserDetailsService {

    private AppUserRepository repository;

    @Autowired
    public AppUserDetailsService(AppUserRepository repository) {
        this.repository = repository;
    }

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
```

```java

public interface AppUserRepository  extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByUsername(String  username);
    Optional<AppUser> findByBankUserId(Long bankUserId);
}
```

```java
{
      "name": "admin-invoices",
      "event": [],
      "request": {
        "method": "GET",
        "header": [],
        "auth": {
          "type": "basic",
          "basic": [
            {
              "key": "password",
              "value": "",
              "type": "string"
            },
            {
              "key": "username",
              "value": "demomanager",
              "type": "string"
            }
          ]
        },
        "description": "",
        "url": {
          "raw": "http://localhost:8080/api/v1/shopping/admin/invoices?paymentId=2",
          "protocol": "http",
          "host": [
            "localhost:8080"
          ],
          "path": [
            "api",
            "v1",
            "shopping",
            "admin",
            "invoices"
          ],
          "query": [
            {
              "key": "paymentId",
              "value": "2"
            }
          ],
          "variable": []
        }
      }
    },
```

```sql

CREATE TABLE app_user_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    site_user_id INT
);

```

```sql

INSERT INTO app_user_details (username, password, role,site_user_id)
VALUES ('mandemo@openecommerce.com', '$2a$12$dF9rbETRkbyzkqjiuFLWAetzdIjqT2nraOZXaVEW.zeEbjxqjM/B6', 'SYSADMIN',50);
INSERT INTO app_user_details (username, password, role,site_user_id)
VALUES ('cusdemo@openecommerce.com', '$2a$12$dF9rbETRkbyzkqjiuFLWAetzdIjqT2nraOZXaVEW.zeEbjxqjM/B6', 'CUSTOMER',49);
INSERT INTO app_user_details (username, password, role,site_user_id)
VALUES ('empdemo@openecommerce.com', '$2a$12$dF9rbETRkbyzkqjiuFLWAetzdIjqT2nraOZXaVEW.zeEbjxqjM/B6', 'EMPLOYEE',48);
```