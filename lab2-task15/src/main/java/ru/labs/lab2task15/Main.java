package ru.labs.lab2task15;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput(new Scanner(System.in));
        int n = input.readInt("Количество студентов n (квадратная матрица n x n): ", 2, 1000);
        int mode = input.readInt("Ввод: 1 — вручную, 2 — случайно: ", 1, 2);
        int[][] matrix = new int[n][n];
        Set<Integer> used = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int id;
            do {
                id =
                        mode == 2
                                ? 100000 + random.nextInt(900000)
                                : input.readInt(
                                        "Номер зачётки студента " + (i + 1) + ": ", 100000, 999999);
                if (used.contains(id) && mode == 1) {
                    System.out.println("Такой номер уже есть.");
                }
            } while (!used.add(id));
            matrix[i][0] = id;
            for (int j = 1; j < n; j++) {
                matrix[i][j] =
                        mode == 2
                                ? 2 + random.nextInt(4)
                                : input.readInt("Оценка по предмету " + j + ": ", 2, 5);
            }
        }
        System.out.println("Исходные результаты:");
        print(matrix);
        System.out.println("Обычная реализация:");
        System.out.println("Отчисленные (есть хотя бы одна двойка):");
        for (int[] row : matrix) {
            if (SessionResults.isDebtor(row)) {
                System.out.println(row[0]);
            }
        }
        int[][] result = SessionResults.expelAndSort(matrix);
        System.out.println("Оставшиеся студенты по убыванию среднего балла:");
        print(result);
        if (result.length == 0) {
            System.out.println("Студентов без задолженностей нет.");
        }
        System.out.println("Реализация через Stream API:");
        System.out.println("Отчисленные (есть хотя бы одна двойка):");
        Arrays.stream(matrix)
                .filter(SessionResultsStream::isDebtor)
                .forEach(row -> System.out.println(row[0]));
        int[][] streamResult = SessionResultsStream.expelAndSort(matrix);
        System.out.println("Оставшиеся студенты по убыванию среднего балла (Stream API):");
        Arrays.stream(streamResult)
                .forEach(
                        row ->
                                System.out.printf(
                                        "%s; средний балл %.2f%n",
                                        Arrays.toString(row), SessionResultsStream.average(row)));
        if (streamResult.length == 0) {
            System.out.println("Студентов без задолженностей нет.");
        }
    }

    private static void print(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.printf(
                    "%s; средний балл %.2f%n", Arrays.toString(row), SessionResults.average(row));
        }
    }
}
