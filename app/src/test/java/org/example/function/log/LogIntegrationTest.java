package org.example.function.log;

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
public class LogIntegrationTest {

    @Mock private MathFunction lnMock;

    private LogBase log2;
    private LogBase log10;
    private final double EPS = 0.001;

    @BeforeEach
    void setUp() {
        log2 = new LogBase(lnMock, 2.0);
        log10 = new LogBase(lnMock, 10.0);
    }

    @ParameterizedTest
    @CsvSource({
            "2.0, 0.6931",  // ln(2)=0.6931 -> log2(2)=1
            "4.0, 1.3863",  // ln(4)=1.3863 -> log2(4)=2
            "8.0, 2.0794"   // ln(8)=2.0794 -> log2(8)=3
    })
    void testLog2WithLnStub(double x, double lnXStubValue) {
        if (x != 2.0) {
            when(lnMock.calculate(eq(2.0), anyDouble())).thenReturn(0.6931);
        }
        when(lnMock.calculate(eq(x), anyDouble())).thenReturn(lnXStubValue);
        
        double result = log2.calculate(x, EPS);
        assertEquals(lnXStubValue / 0.6931, result, EPS);
    }

    @Test
    void testLogBeyondDomain() {
        double x = 0.0;
        double result = log2.calculate(x, EPS);
        assertTrue(Double.isNaN(result));
    }

    @ParameterizedTest
    @CsvSource({
            "10.0, 2.3025",   // ln(10)=2.3025 -> log10(10)=1
            "100.0, 4.6051"   // ln(100)=4.6051 -> log10(100)=2
    })
    void testLog10WithLnStub(double x, double lnXStubValue) {
        if (x != 10.0) {
            when(lnMock.calculate(eq(10.0), anyDouble())).thenReturn(2.3025);
        }
        when(lnMock.calculate(eq(x), anyDouble())).thenReturn(lnXStubValue);
        
        double result = log10.calculate(x, EPS);
        assertEquals(lnXStubValue / 2.3025, result, EPS);
    }
}
