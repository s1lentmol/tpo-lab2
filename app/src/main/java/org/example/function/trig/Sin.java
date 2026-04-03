package org.example.function.trig;

import org.example.function.core.MathFunction;

public class Sin implements MathFunction {
    @Override
    public double calculate(double x, double eps) {
        double normX = normalize(x);
        double result = 0;
        double term = normX;
        long n = 1;
        while (Math.abs(term) >= eps) {
            result += term;
            term = -term * normX * normX / ((2 * n) * (2 * n + 1));
            n++;
        }
        return result;
    }

    private double normalize(double x) {
        double pi = 3.14159265358979323846;
        while (x > pi) x -= 2 * pi;
        while (x < -pi) x += 2 * pi;
        return x;
    }
}
