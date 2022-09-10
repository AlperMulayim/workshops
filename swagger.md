### SWAGGER DOCUMENTATION

`````java
@SpringBootApplication
@EnableSwagger2
public class CouponearApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponearApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()                 .apis(RequestHandlerSelectors.basePackage("com.alper"))
				.paths(regex("/api.*"))
				.build();

	}

}
```````

### application.yml
`````yml
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
``````

