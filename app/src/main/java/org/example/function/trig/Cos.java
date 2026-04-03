package org.example.function.trig;

import org.example.function.core.MathFunction;

public class Cos implements MathFunction {
    private final MathFunction sin;

    public Cos(MathFunction sin) {
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double eps) {
        // cos(x) = sin(x + PI / 2)
        return sin.calculate(x + 3.14159265358979323846 / 2, eps);
    }
}
