package org.example.function.log;

import org.example.function.core.MathFunction;

public class Ln implements MathFunction {
    @Override
    public double calculate(double x, double eps) {
        if (x <= 0) {
            return Double.NaN;
        }
        
        double y = (x - 1) / (x + 1);
        double result = 0;
        double term = y;
        double ySquared = y * y;
        int n = 1;
        
        while (Math.abs(term) >= eps) {
            result += term / (2 * n - 1);
            term *= ySquared;
            n++;
        }
        return 2 * result;
    }
}
