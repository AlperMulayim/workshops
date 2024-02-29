package org.example.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MockitoCalculatorTest {

    @InjectMocks
    private Calculator calculator;
    @Mock
    private CalculatorMetrics metrics;


    @Test
    public void testMetrics() throws Exception {
        Mockito.when(metrics.getMetrics(Mockito.anyString())).thenReturn("milimetre");
        assertEquals( "milimetre",calculator.getMetrics("mm"));
    }


    @Test
    public void testException() throws Exception {
        Mockito.when(metrics.getMetrics("m")).thenReturn("A");
        assertThrows(Exception.class,()->calculator.getMetrics("mm"));
    }

    @Test //check if one time call for metrics get.
    public void  testVerify() throws Exception {
        Mockito.when(metrics.getMetrics("m")).thenReturn("metre");
        assertEquals("metre",calculator.getMetrics("m"));
        Mockito.verify(metrics, Mockito.times(1)).getMetrics("m");
    }
}
