package org.example.function;

import org.example.function.core.MathFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FunctionSystemIntegrationTest {

    @Mock private MathFunction sinMock;
    @Mock private MathFunction cosMock;
    @Mock private MathFunction secMock;
    @Mock private MathFunction log2Mock;
    @Mock private MathFunction log3Mock;
    @Mock private MathFunction log10Mock;

    private FunctionSystem functionSystem;
    private final double EPS = 0.001;

    @BeforeEach
    void setUp() {
        functionSystem = new FunctionSystem(sinMock, cosMock, secMock, log2Mock, log3Mock, log10Mock);
    }

    @ParameterizedTest
    @CsvSource({
            "-0.5, -0.479, 0.877, 1.139",
            "-1.0, -0.841, 0.540, 1.850",
            "-3.14159, 0.0, -1.0, -1.0"
    })
    void testSystemFunctionWithNegativeValues(double x, double expectedSin, double expectedCos, double expectedSec) {
        when(sinMock.calculate(eq(x), anyDouble())).thenReturn(expectedSin);
        when(cosMock.calculate(eq(x), anyDouble())).thenReturn(expectedCos);
        when(secMock.calculate(eq(x), anyDouble())).thenReturn(expectedSec);

        double expectedFormula1 = Math.pow((expectedSin + expectedCos) - expectedCos, 3) * expectedSec;
        double expectedFormula2 = expectedCos / (expectedSin - expectedCos);
        double expectedResult = expectedFormula1 + expectedFormula2;

        double result = functionSystem.calculate(x, EPS);
        assertEquals(expectedResult, result, EPS);
    }

    @ParameterizedTest
    @CsvSource({
            "2.0, 1.0, 0.6309, 0.301",
            "10.0, 3.3219, 2.0959, 1.0",
            "0.5, -1.0, -0.6309, -0.301"
    })
    void testSystemFunctionWithPositiveValues(double x, double expectedLog2, double expectedLog3, double expectedLog10) {
        when(log2Mock.calculate(eq(x), anyDouble())).thenReturn(expectedLog2);
        when(log3Mock.calculate(eq(x), anyDouble())).thenReturn(expectedLog3);
        when(log10Mock.calculate(eq(x), anyDouble())).thenReturn(expectedLog10);

        double term1 = expectedLog10 / expectedLog2;
        double term2 = term1 + expectedLog2;
        double term3 = expectedLog10 * (expectedLog2 + expectedLog10);
        double termToCube = term2 - term3;
        double expectedResult = Math.pow(termToCube, 3) + expectedLog3;

        double result = functionSystem.calculate(x, EPS);
        assertEquals(expectedResult, result, EPS);
    }
}
