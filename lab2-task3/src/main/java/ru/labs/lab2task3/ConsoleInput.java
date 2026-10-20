package ru.labs.lab2task3;

import java.util.Scanner;

public final class ConsoleInput {
    private final Scanner scanner;

    public ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNext()) {
                throw new IllegalStateException("Ввод закончился");
            }
            String token = scanner.next();
            try {
                int value = Integer.parseInt(token);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {

            }
            System.out.printf("Введите целое число от %d до %d.%n", min, max);
        }
    }

    public long readNatural(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNext()) {
                throw new IllegalStateException("Ввод закончился");
            }
            try {
                long value = Long.parseLong(scanner.next());
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {

            }
            System.out.println("Нужно натуральное число от 1 до " + Long.MAX_VALUE);
        }
    }
}
