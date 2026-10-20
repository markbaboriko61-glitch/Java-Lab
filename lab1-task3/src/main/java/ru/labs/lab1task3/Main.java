package ru.labs.lab1task3;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput(new Scanner(System.in));
        int count = input.readInt("Количество чисел: ", 1, 100_000);
        for (int i = 0; i < count; i++) {
            long number = input.readNatural("Число " + (i + 1) + ": ");
            List<Long> factors = PrimeFactorization.factorize(number);
            String result =
                    factors.isEmpty()
                            ? "простых множителей нет"
                            : factors.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(" * "));
            System.out.println(number + " = " + result);
        }
    }
}
