package org.example.course;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RepeatedTestTutorial {

    @RepeatedTest(10)
    public void divisionFailTest(RepetitionInfo repetitionInfo){
        Calculator calculator = new Calculator();
        System.out.println("test->  4/2 is not equal 3" );
        assertNotEquals(3,calculator.division(4,2).intValue());
        System.out.println("repitation: " + repetitionInfo.getCurrentRepetition());
    }
}
