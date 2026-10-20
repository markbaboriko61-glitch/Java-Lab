package ru.labs.lab3task15;

import java.util.Scanner;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = readCount(scanner);
        SymbolSet[] sets = new SymbolSet[count];
        for (int i = 0; i < count; i++) {
            System.out.println("Символы множества " + i + " (пустая строка — пустое множество):");
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Ввод закончился");
            }
            sets[i] = new SymbolSet(scanner.nextLine());
        }
        System.out.println("Проверка принадлежности: введите один символ:");
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Ввод закончился");
        }
        String symbol = scanner.nextLine();
        if (symbol.codePointCount(0, symbol.length()) == 1) {
            System.out.println(
                    "Входит в первое множество: " + sets[0].contains(symbol.codePointAt(0)));
        } else {
            System.out.println("Проверка пропущена: введён не один символ.");
        }
        for (int i = 0; i < sets.length; i++) {
            if (sets[i].size() > 0) {
                System.out.println(
                        "Первый символ множества "
                                + i
                                + ": "
                                + new String(Character.toChars(sets[i].get(0))));
            }
            for (int j = i + 1; j < sets.length; j++) {
                System.out.printf("Пара (%d, %d):%n", i, j);
                System.out.println("Объединение: " + sets[i].add(sets[j]));
                System.out.println("Разность первого и второго: " + sets[i].subtract(sets[j]));
                System.out.println("Пересечение: " + sets[i].multiply(sets[j]));
                System.out.println(
                        "Только в одном: " + SetOperations.symmetricDifference(sets[i], sets[j]));
            }
        }
        SymbolSet copy = new SymbolSet("");
        copy.assign(sets[0]);
        System.out.println("Присваивание копии первого множества: " + copy);
    }

    private static int readCount(Scanner scanner) {
        while (true) {
            System.out.println("Количество множеств (2..100):");
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Ввод закончился");
            }
            try {
                int count = Integer.parseInt(scanner.nextLine().trim());
                if (count >= 2 && count <= 100) {
                    return count;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Неверное количество.");
        }
    }
}
