package org.example.function;

import org.example.function.core.MathFunction;

public class FunctionSystem implements MathFunction {
    private final MathFunction sin;
    private final MathFunction cos;
    private final MathFunction sec;
    private final MathFunction log2;
    private final MathFunction log3;
    private final MathFunction log10;

    public FunctionSystem(MathFunction sin, MathFunction cos, MathFunction sec,
                          MathFunction log2, MathFunction log3, MathFunction log10) {
        this.sin = sin;
        this.cos = cos;
        this.sec = sec;
        this.log2 = log2;
        this.log3 = log3;
        this.log10 = log10;
    }

    @Override
    public double calculate(double x, double eps) {
        if (x <= 0) {
            double sinVal = sin.calculate(x, eps);
            double cosVal = cos.calculate(x, eps);
            double secVal = sec.calculate(x, eps);
            
            // (((sin(x) + cos(x)) - cos(x))^3) * sec(x) + (cos(x) / (sin(x) - cosx)))
            double part1 = Math.pow((sinVal + cosVal) - cosVal, 3) * secVal;
            double part2 = cosVal / (sinVal - cosVal);
            return part1 + part2;
        } else {
            double l2 = log2.calculate(x, eps);
            double l3 = log3.calculate(x, eps);
            double l10 = log10.calculate(x, eps);
            
            // (((log_10(x) / log_2(x)) + log_2(x)) - (log_10(x) * (log_2(x) + log_10(x))))^3 + log_3(x)
            double term1 = l10 / l2;
            double term2 = term1 + l2;
            double term3 = l10 * (l2 + l10);
            
            double termToCube = term2 - term3;
            
            return Math.pow(termToCube, 3) + l3;
        }
    }
}
