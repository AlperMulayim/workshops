package org.example.course;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ParameterizedTestTutorial {

    private Calculator calculator;

    @BeforeEach
    private void  setup(){
        calculator = new Calculator();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/paramTestDivide.csv")
    public void parametarizedTest(Integer a, Integer b, Integer result){
        Assertions.assertEquals(result, calculator.division(a, b));
    }

}
