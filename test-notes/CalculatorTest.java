package org.example.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit Test and Mockito Course Examples")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)  //test method will share same class instance.
public class CalculatorTest {

    @Test
    public void divisionTest(){
        Calculator calculator = new Calculator();
        assertEquals(2,calculator.division(4,2).intValue(),"4/2 did not produce 2");
    }

    @Test
    public void divisionFailTest(){
        Calculator calculator = new Calculator();
        assertNotEquals(3,calculator.division(4,2).intValue());
    }

    //name for unit test
    //test<System UnderTest>_<Conditions_Changer>_<Expected_Results>

    @Test
    public void testIntegerDevision_WhenDividerValidInteger4Divide2_Return2(){
        Calculator calculator = new Calculator();
        assertEquals(2,calculator.division(4,2).intValue(),"4/2 did not produce 2");
    }

    @DisplayName("Integer Division to 0 causes ArithmeticException")
    @Test
    public void testIntegerDivision_WhenDividerIsZero_ThrowAritmeticException(){
        Calculator calculator = new Calculator();
        assertThrows(ArithmeticException.class,()->calculator.division(1,0));
    }

    // ARRANGE - ACT - ASSERT
}