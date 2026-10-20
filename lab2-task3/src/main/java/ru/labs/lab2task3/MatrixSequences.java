package ru.labs.lab2task3;

import java.util.Arrays;
import java.util.Comparator;

public final class MatrixSequences {
    private MatrixSequences() {}

    public record Run(int start, int length) {}

    public static Run longestRun(int[] row, boolean increasing) {
        if (row.length == 0) {
            return new Run(0, 0);
        }
        int bestStart = 0;
        int bestLength = 1;
        int currentStart = 0;
        for (int i = 1; i < row.length; i++) {
            boolean continues = increasing ? row[i] > row[i - 1] : row[i] < row[i - 1];
            if (!continues) {
                currentStart = i;
            }
            int length = i - currentStart + 1;
            if (length > bestLength) {
                bestStart = currentStart;
                bestLength = length;
            }
        }
        return new Run(bestStart, bestLength);
    }

    public static int[][] sortedRows(int[][] source, boolean increasing, boolean ascending) {
        int[][] result = Arrays.stream(source).map(int[]::clone).toArray(int[][]::new);
        Comparator<int[]> comparator =
                Comparator.comparingInt(row -> longestRun(row, increasing).length());
        if (!ascending) {
            comparator = comparator.reversed();
        }

        Arrays.sort(result, comparator);
        return result;
    }

    public static int[][] jagged(int[][] source, boolean increasing) {
        int[][] result = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            Run run = longestRun(source[i], increasing);
            result[i] = Arrays.copyOfRange(source[i], run.start(), run.start() + run.length());
        }
        return result;
    }
}
