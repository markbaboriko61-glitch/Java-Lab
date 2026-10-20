package ru.labs.lab2task15;

import java.util.Arrays;
import java.util.Comparator;

public final class SessionResultsStream {
    private SessionResultsStream() {}

    public static void validate(int[][] matrix) {
        if (matrix.length == 0) {
            return;
        }
        int columns = matrix[0].length;
        if (columns < 2 || Arrays.stream(matrix).anyMatch(row -> row.length != columns)) {
            throw new IllegalArgumentException("Нужны номер зачётки и оценки");
        }
        if (Arrays.stream(matrix).mapToInt(row -> row[0]).anyMatch(id -> id < 100000 || id > 999999)
                || Arrays.stream(matrix).mapToInt(row -> row[0]).distinct().count()
                        != matrix.length) {
            throw new IllegalArgumentException(
                    "Номер зачётки должен быть уникальным и шестизначным");
        }
        if (Arrays.stream(matrix)
                .flatMapToInt(row -> Arrays.stream(row, 1, row.length))
                .anyMatch(mark -> mark < 2 || mark > 5)) {
            throw new IllegalArgumentException("Оценки: от 2 до 5");
        }
    }

    public static boolean isDebtor(int[] student) {
        return Arrays.stream(student, 1, student.length).anyMatch(mark -> mark == 2);
    }

    public static double average(int[] student) {
        if (student.length < 2) {
            throw new IllegalArgumentException("Нет оценок");
        }
        return Arrays.stream(student, 1, student.length).average().orElseThrow();
    }

    public static int[][] expelAndSort(int[][] source) {
        validate(source);
        return Arrays.stream(source)
                .filter(row -> !isDebtor(row))
                .map(int[]::clone)
                .sorted(Comparator.<int[]>comparingDouble(SessionResultsStream::average).reversed())
                .toArray(int[][]::new);
    }
}
