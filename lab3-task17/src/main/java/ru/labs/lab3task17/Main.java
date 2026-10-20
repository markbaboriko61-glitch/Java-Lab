package ru.labs.lab3task17;

import java.util.Scanner;
import java.util.function.Consumer;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите элементы массива одной строкой через пробел (можно пустую):");
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Ввод закончился");
        }
        String line = scanner.nextLine().trim();
        try {
            String[] tokens = line.isEmpty() ? new String[0] : line.split("\\s+");
            int[] source = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                source[i] = Integer.parseInt(tokens[i]);
            }
            print("Пузырьковая", source, IntArray::bubbleSort);
            print("Шейкерная", source, IntArray::shakerSort);
            print("Выбором", source, IntArray::selectionSort);
            print("Адресная (хеширование)", source, IntArray::addressSort);
            print("Вставками", source, IntArray::insertionSort);
            print("Бинарного слияния", source, IntArray::mergeSort);
            print("Шелла", source, IntArray::shellSort);
        } catch (NumberFormatException exception) {
            System.out.println("Ошибка: нужны целые числа типа int.");
        }
    }

    private static void print(String label, int[] source, Consumer<IntArray> algorithm) {
        IntArray copy = new IntArray(source);
        algorithm.accept(copy);
        System.out.println(label + ": " + copy);
    }
}
