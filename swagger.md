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

### pom.xml

`````

		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.6.1</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.6.1</version>
		</dependency>
		
``````		
### SPRING BOOT v3 integration SpringDoc. https://springdoc.org/migrating-from-springfox.html


``````

   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.2.0</version>
   </dependency>

``````
