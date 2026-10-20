package ru.labs.lab1task27;

public final class DigitFilter {
    private DigitFilter() {}

    public static boolean matches(long number, int digits) {
        if (number <= 0 || digits < 1 || digits > 19) {
            throw new IllegalArgumentException("Нужно натуральное число и 1 <= p <= 19");
        }
        int[] frequency = new int[10];
        int length = 0;
        while (number > 0) {
            frequency[(int) (number % 10)]++;
            number /= 10;
            length++;
        }
        return length == digits
                && frequency[0] == 1
                && frequency[2] == 1
                && frequency[3] == 1
                && frequency[7] == 1;
    }
}
