package org.example.function.trig;

import org.example.function.core.MathFunction;

public class Sec implements MathFunction {
    private final MathFunction cos;

    public Sec(MathFunction cos) {
        this.cos = cos;
    }

    @Override
    public double calculate(double x, double eps) {
        double cosVal = cos.calculate(x, eps);
        if (Math.abs(cosVal) < eps) {
            return Double.NaN;
        }
        return 1.0 / cosVal;
    }
}
