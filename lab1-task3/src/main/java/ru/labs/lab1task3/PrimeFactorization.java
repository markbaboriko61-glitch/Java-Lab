package ru.labs.lab1task3;

import java.util.ArrayList;
import java.util.List;

public final class PrimeFactorization {
    private PrimeFactorization() {}

    public static List<Long> factorize(long number) {
        if (number < 1) {
            throw new IllegalArgumentException("Число должно быть натуральным");
        }
        List<Long> factors = new ArrayList<>();
        while (number % 2 == 0) {
            factors.add(2L);
            number /= 2;
        }

        for (long divisor = 3; divisor <= number / divisor; divisor += 2) {
            while (number % divisor == 0) {
                factors.add(divisor);
                number /= divisor;
            }
        }
        if (number > 1) {
            factors.add(number);
        }
        return factors;
    }
}
