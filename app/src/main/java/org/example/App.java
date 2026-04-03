package org.example;

import org.example.function.FunctionSystem;
import org.example.function.log.Ln;
import org.example.function.log.LogBase;
import org.example.function.trig.Cos;
import org.example.function.trig.Sec;
import org.example.function.trig.Sin;
import org.example.function.utils.CsvExporter;
import org.example.function.core.MathFunction;

import java.io.File;

public class App {
    public static void main(String[] args) {
        MathFunction sin = new Sin();
        MathFunction cos = new Cos(sin);
        MathFunction sec = new Sec(cos);
        MathFunction ln = new Ln();
        MathFunction log2 = new LogBase(ln, 2.0);
        MathFunction log3 = new LogBase(ln, 3.0);
        MathFunction log10 = new LogBase(ln, 10.0);
        
        FunctionSystem system = new FunctionSystem(sin, cos, sec, log2, log3, log10);
        
        System.out.println("Генерация CSV-файлов для построения графиков...");
        
        // Создаем папку для отчетов
        File dir = new File("csv_output");
        if(!dir.exists()) dir.mkdir();

        double eps = 0.0001;
        double step = 0.1;

        CsvExporter.exportToCsv(sin, -10.0, 0.0, step, eps, "csv_output/sin.csv");
        CsvExporter.exportToCsv(cos, -10.0, 0.0, step, eps, "csv_output/cos.csv");
        CsvExporter.exportToCsv(sec, -10.0, 0.0, step, eps, "csv_output/sec.csv");

        CsvExporter.exportToCsv(ln, 0.1, 10.0, step, eps, "csv_output/ln.csv");
        CsvExporter.exportToCsv(log2, 0.1, 10.0, step, eps, "csv_output/log2.csv");
        CsvExporter.exportToCsv(log10, 0.1, 10.0, step, eps, "csv_output/log10.csv");

        CsvExporter.exportToCsv(system, -5.0, 5.0, step, eps, "csv_output/system.csv");
    }
}
