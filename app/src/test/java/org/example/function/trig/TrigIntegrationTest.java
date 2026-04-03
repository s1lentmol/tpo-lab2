package org.example.function.trig;

import org.example.function.core.MathFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrigIntegrationTest {

    @Mock private MathFunction sinMock;
    @Mock private MathFunction cosMock;

    private Cos cos;
    private Sec sec;
    private final double EPS = 0.001;

    @BeforeEach
    void setUp() {
        cos = new Cos(sinMock);
        sec = new Sec(cosMock);
    }

    @ParameterizedTest
    @CsvSource({
            "0.0, 1.0",     // sin(0 + PI/2) = 1.0 (cos(0))
            "-3.14159, -1.0", // sin(-PI + PI/2) = -1.0 (cos(-pi))
            "-1.57079, 0.0"   // sin(-PI/2 + PI/2) = 0.0 (cos(-pi/2))
    })
    void testCosWithSinStub(double x, double expectedSinStubResult) {
        when(sinMock.calculate(eq(x + Math.PI / 2), anyDouble())).thenReturn(expectedSinStubResult);
        
        double result = cos.calculate(x, EPS);
        assertEquals(expectedSinStubResult, result, EPS);
    }

    @ParameterizedTest
    @CsvSource({
            "0.0, 1.0",        // cos(0) = 1 -> sec(0) = 1
            "-3.14159, -1.0",  // cos(-pi) = -1 -> sec(-pi) = -1
            "-1.0, 0.5403"     // cos(-1) = 0.5403 -> sec(-1) = 1.8508
    })
    void testSecWithCosStub(double x, double cosStubValue) {
        when(cosMock.calculate(eq(x), anyDouble())).thenReturn(cosStubValue);
        
        double result = sec.calculate(x, EPS);
        assertEquals(1.0 / cosStubValue, result, EPS);
    }

    @Test
    void testSecAtAsymptote() {
        double x = -Math.PI / 2;
        when(cosMock.calculate(eq(x), anyDouble())).thenReturn(0.0);
        
        double result = sec.calculate(x, EPS);
        assertTrue(Double.isNaN(result));
    }
}
