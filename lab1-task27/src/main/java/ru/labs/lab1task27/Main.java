package ru.labs.lab1task27;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput(new Scanner(System.in));
        int count = input.readInt("Количество чисел: ", 1, 100_000);
        int digits = input.readInt("Количество цифр p: ", 1, 19);
        List<Long> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            long number = input.readNatural("Число " + (i + 1) + ": ");
            if (DigitFilter.matches(number, digits)) {
                result.add(number);
            }
        }
        System.out.println("Подходящие числа: " + result);
        System.out.println("Количество: " + result.size());
    }
}
