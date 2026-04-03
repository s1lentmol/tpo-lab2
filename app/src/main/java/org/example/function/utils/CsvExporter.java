package org.example.function.utils;

import org.example.function.core.MathFunction;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

public class CsvExporter {

    public static void exportToCsv(MathFunction function, double start, double end, double step, double eps, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("X,Result");
            for (double x = start; x <= end; x += step) {
                double result = function.calculate(x, eps);
                writer.printf(Locale.US, "%.5f,%.5f%n", x, result);
            }
            System.out.println("Экспорт завершен: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в CSV файл: " + e.getMessage());
        }
    }
}
