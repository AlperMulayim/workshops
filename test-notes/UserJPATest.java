package com.appsdeveloperblog.tutorials.junit;

//seperately test the data layer
//Validate Entity Classes.

import com.appsdeveloperblog.tutorials.junit.io.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.sql.SQLDataException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserJPATest {

    @Autowired
    TestEntityManager testEntityManager;

    private UserEntity userEntity;
    private UserEntity secondUser;
    @BeforeEach
    void init(){
        userEntity = new UserEntity();
        userEntity.setFirstName("alper");
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setLastName("mulayim");
        userEntity.setEncryptedPassword("abc1234123");
        userEntity.setEmail("alper@alper.com");

        secondUser = new UserEntity();
        secondUser.setFirstName("alP");
        secondUser.setUserId(UUID.randomUUID().toString());
        secondUser.setLastName("mul");
        secondUser.setEncryptedPassword("abc1234123");
        secondUser.setEmail("alper@alpe21r.com");
    }
    @Test
    void testUserEntityWhenValidProvidedReturnStoredData(){

        UserEntity result = testEntityManager.persistAndFlush(userEntity);
        assertEquals(userEntity.getFirstName(),result.getFirstName());
        assertNotNull(result.getUserId());
        assertEquals(result.getEmail(),userEntity.getEmail());
        assertTrue(result.getId() > 0);
    }

    @Test
    void testFirstNameIsTooLongShouldExceptionThrown(){
        userEntity.setFirstName("sdadsadadasdsdsdasdsadsdadasdasdsadasdsdadasdasdsaddda");
        assertThrows(PersistenceException.class, ()->testEntityManager.persistAndFlush(userEntity));
    }

    @Test
    void testUserEntityUserIdUnique(){
        String userId = UUID.randomUUID().toString();

        userEntity.setUserId("userId");
        secondUser.setUserId("userId");

        testEntityManager.persistAndFlush(userEntity);
        assertThrows(PersistenceException.class, ()->testEntityManager.persistAndFlush(secondUser));

    }

}
