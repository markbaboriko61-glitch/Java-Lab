package ru.labs.lab1task15;

public final class DigitAnalyzer {
    private DigitAnalyzer() {}

    public record Result(int maximumDigit, int occurrences) {}

    public static Result analyze(long number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Число должно быть натуральным");
        }
        int maximum = -1;
        int count = 0;
        while (number > 0) {
            int digit = (int) (number % 10);
            if (digit > maximum) {
                maximum = digit;
                count = 1;
            } else if (digit == maximum) {
                count++;
            }
            number /= 10;
        }
        return new Result(maximum, count);
    }
}
