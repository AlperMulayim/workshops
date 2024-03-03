package com.appsdeveloperblog.tutorials.junit;

import com.appsdeveloperblog.tutorials.junit.security.SecurityConstants;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//integration test service controller and repository.
//only beans will created to related to test class.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//properties = "server.port=8089")
//@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //shared instance in order to test with JWT token
public class UserControllerIntegrationTest {

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String  token;
    @Test
    void contextLoad(){
        System.out.println("Server started: " + port);
    }

    @Test
    @Order(1)
    void testCreateUserValidProvidedReturnUserDetails() throws JSONException {
        JSONObject detailsObject = new JSONObject();
        detailsObject.put("firstName","alper");
        detailsObject.put("lastName","mulayim");
        detailsObject.put("email","test3@test.com");
        detailsObject.put("password","12345678");
        detailsObject.put("repeatPassword","12345678");


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        HttpEntity<String> request = new HttpEntity<>(detailsObject.toString(),headers);

       // ResponseEntity<String> resultStr =  testRestTemplate.postForEntity("/users",request,String.class);

        ResponseEntity<UserRest> result =  testRestTemplate.postForEntity("/users",request, UserRest.class);

        assertEquals(detailsObject.get("firstName"),result.getBody().getFirstName());
        assertEquals(HttpStatus.OK.value(),result.getStatusCode().value());
    }

    @Test
    @Order(2)
    void testGetMethodForbidden(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept","application/json");

        HttpEntity requestEntity = new HttpEntity(null,headers);

        ResponseEntity<List<UserRest>> response = testRestTemplate
                .exchange("/users", HttpMethod.GET, requestEntity,
                        new ParameterizedTypeReference<List<UserRest>>() {
                });

        assertNotEquals(HttpStatus.OK.value(),response.getStatusCode().value());
        assertEquals(HttpStatus.FORBIDDEN.value(),response.getStatusCode().value());

    }

    @Test
    @Order(3)
    void testWithLoginValidCredentials() throws JSONException {
        JSONObject credentials = new JSONObject();
        credentials.put("email","test3@test.com");
        credentials.put("password","12345678");

        HttpEntity<String> request = new HttpEntity<>(credentials.toString());
        ResponseEntity response = testRestTemplate.postForEntity("/users/login",request,null);

        token = response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0);

        assertEquals(HttpStatus.OK.value(),response.getStatusCode().value());
        assertNotNull(response.getHeaders().getValuesAsList("Authorizatio"));
    }

    @Test
    @Order(4)
    void testWithJWTTokenGetUsers(){
        //get access token
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        HttpEntity request = new HttpEntity(null,headers);

        ResponseEntity<List<UserRest>>  responseEntity = testRestTemplate.exchange(
                 "/users",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<UserRest>>() {
                }
        );

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().size() == 1);

    }

}































