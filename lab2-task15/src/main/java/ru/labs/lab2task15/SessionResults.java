package ru.labs.lab2task15;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public final class SessionResults {
    private SessionResults() {}

    public static void validate(int[][] matrix) {
        Set<Integer> identifiers = new HashSet<>();
        int columns = matrix.length == 0 ? 0 : matrix[0].length;
        for (int[] row : matrix) {
            if (row.length != columns || columns < 2) {
                throw new IllegalArgumentException("Нужны номер зачётки и оценки");
            }
            if (row[0] < 100000 || row[0] > 999999 || !identifiers.add(row[0])) {
                throw new IllegalArgumentException(
                        "Номер зачётки должен быть уникальным и шестизначным");
            }
            for (int j = 1; j < row.length; j++) {
                if (row[j] < 2 || row[j] > 5) {
                    throw new IllegalArgumentException("Оценки: от 2 до 5");
                }
            }
        }
    }

    public static boolean isDebtor(int[] student) {
        for (int j = 1; j < student.length; j++) {
            if (student[j] == 2) {
                return true;
            }
        }
        return false;
    }

    public static double average(int[] student) {
        if (student.length < 2) {
            throw new IllegalArgumentException("Нет оценок");
        }
        long sum = 0;
        for (int j = 1; j < student.length; j++) {
            sum += student[j];
        }
        return (double) sum / (student.length - 1);
    }

    public static int[][] expelAndSort(int[][] source) {
        validate(source);
        return Arrays.stream(source)
                .filter(row -> !isDebtor(row))
                .map(int[]::clone)
                .sorted(Comparator.<int[]>comparingDouble(SessionResults::average).reversed())
                .toArray(int[][]::new);
    }
}
