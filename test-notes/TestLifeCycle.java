package org.example.course;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//@TestMethodOrder(MethodOrderer.Random.class)
public class TestLifeCycle {

    Calculator calculator;
    @BeforeAll
    public static void setup(){
        System.out.println("Test Start");
        //PREPARE SETUP FOR CLASS
    }

    @BeforeEach
    public  void beforeEach(){
        calculator = new Calculator();
        System.out.println("Test Each Start");
        //PREPARE SETUP FOR each methods
    }

    @AfterEach
    public  void afterEach(){
        System.out.println("Test Each End");
    }

    @AfterAll
    public static void complete(){
        System.out.println("Test Completed");
    }

    @Test
    public void divisionFailTest(){
        assertNotEquals(3,calculator.division(4,2).intValue());
    }

    @Test
    public void testIntegerDevision_WhenDividerValidInteger4Divide2_Return2(){
        assertEquals(2,calculator.division(4,2).intValue(),"4/2 did not produce 2");
    }

    @Disabled("Test Disabled, not completed to implement yet")
    @Test
    public void testDisable(){
        assertEquals(2,calculator.division(4,2).intValue(),"4/2 did not produce 2");
    }

}
