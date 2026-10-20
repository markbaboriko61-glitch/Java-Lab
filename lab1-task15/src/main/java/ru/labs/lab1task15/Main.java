package ru.labs.lab1task15;

import java.util.Scanner;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput(new Scanner(System.in));
        int count = input.readInt("Количество чисел: ", 1, 100_000);
        for (int i = 0; i < count; i++) {
            long number = input.readNatural("Число " + (i + 1) + ": ");
            DigitAnalyzer.Result result = DigitAnalyzer.analyze(number);
            System.out.printf(
                    "%d: максимальная цифра %d, встречается %d раз(а).%n",
                    number, result.maximumDigit(), result.occurrences());
        }
    }
}
