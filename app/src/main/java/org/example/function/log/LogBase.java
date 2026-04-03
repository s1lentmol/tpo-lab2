package org.example.function.log;

import org.example.function.core.MathFunction;

public class LogBase implements MathFunction {
    private final MathFunction ln;
    private final double base;

    public LogBase(MathFunction ln, double base) {
        this.ln = ln;
        this.base = base;
    }

    @Override
    public double calculate(double x, double eps) {
        if (x <= 0) {
            return Double.NaN;
        }
        return ln.calculate(x, eps) / ln.calculate(base, eps);
    }
}
