package ru.labs.lab2task3;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput(new Scanner(System.in));
        int n = input.readInt("Размерность квадратной матрицы n: ", 1, 1000);
        int mode = input.readInt("Ввод: 1 — вручную, 2 — случайно: ", 1, 2);
        int[][] matrix = new int[n][n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] =
                        mode == 2
                                ? random.nextInt(101) - 50
                                : input.readInt(
                                        "a[" + i + "][" + j + "]: ",
                                        Integer.MIN_VALUE,
                                        Integer.MAX_VALUE);
            }
        }
        boolean increasing =
                input.readInt("Последовательность: 1 — возрастающая, 2 — убывающая: ", 1, 2) == 1;
        boolean ascending =
                input.readInt("Порядок длин: 1 — по возрастанию, 2 — по убыванию: ", 1, 2) == 1;
        System.out.println("Исходная матрица:");
        print(matrix);
        for (int i = 0; i < n; i++) {
            System.out.printf(
                    "Строка %d: длина %d%n",
                    i + 1, MatrixSequences.longestRun(matrix[i], increasing).length());
        }
        System.out.println("Упорядоченная матрица:");
        print(MatrixSequences.sortedRows(matrix, increasing, ascending));
        System.out.println("Зубчатый массив из строк ИСХОДНОЙ матрицы:");
        print(MatrixSequences.jagged(matrix, increasing));
    }

    private static void print(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
