package ru.labs.lab3task3;

import java.util.Scanner;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = readCount(scanner);
        ComplexFraction[] fractions = new ComplexFraction[count];
        for (int i = 0; i < count; i++) {
            while (true) {
                System.out.println(
                        "Дробь " + i + ": введите Re(m) Im(m) Re(n) Im(n) одной строкой:");
                if (!scanner.hasNextLine()) {
                    throw new IllegalStateException("Ввод закончился");
                }
                String[] parts = scanner.nextLine().trim().split("\\s+");
                try {
                    if (parts.length != 4) {
                        throw new IllegalArgumentException("Нужны четыре числа");
                    }
                    fractions[i] =
                            new ComplexFraction(
                                    new Complex(
                                            Double.parseDouble(parts[0]),
                                            Double.parseDouble(parts[1])),
                                    new Complex(
                                            Double.parseDouble(parts[2]),
                                            Double.parseDouble(parts[3])));
                    break;
                } catch (IllegalArgumentException exception) {
                    System.out.println("Ошибка: " + exception.getMessage());
                }
            }
        }
        System.out.println("Исходные дроби:");
        print(fractions);
        FractionArrayService.addNextToEvenIndices(fractions);
        System.out.println("После преобразования (индексация с нуля):");
        print(fractions);
    }

    private static int readCount(Scanner scanner) {
        while (true) {
            System.out.println("Количество дробей k (1..10000):");
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Ввод закончился");
            }
            try {
                int count = Integer.parseInt(scanner.nextLine().trim());
                if (count > 0 && count <= 10000) {
                    return count;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Неверное количество.");
        }
    }

    private static void print(ComplexFraction[] fractions) {
        for (int i = 0; i < fractions.length; i++) {
            System.out.println(i + ": " + fractions[i] + " = " + fractions[i].value());
        }
    }
}
