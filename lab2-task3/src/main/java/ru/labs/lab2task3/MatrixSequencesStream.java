package ru.labs.lab2task3;

import ru.labs.lab2task3.MatrixSequences.Run;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public final class MatrixSequencesStream {
    private MatrixSequencesStream() {}

    private record ScoredRow(int[] values, int length) {}

    public static Run longestRun(int[] row, boolean increasing) {
        if (row.length == 0) {
            return new Run(0, 0);
        }
        int[] boundaries =
                IntStream.concat(
                                IntStream.concat(
                                        IntStream.of(0),
                                        IntStream.range(1, row.length)
                                                .filter(
                                                        i ->
                                                                increasing
                                                                        ? row[i] <= row[i - 1]
                                                                        : row[i] >= row[i - 1])),
                                IntStream.of(row.length))
                        .toArray();
        return IntStream.range(0, boundaries.length - 1)
                .mapToObj(i -> new Run(boundaries[i], boundaries[i + 1] - boundaries[i]))
                .reduce(
                        new Run(0, 0),
                        (best, current) -> current.length() > best.length() ? current : best);
    }

    public static int[][] sortedRows(int[][] source, boolean increasing, boolean ascending) {
        Comparator<ScoredRow> order = Comparator.comparingInt(ScoredRow::length);
        if (!ascending) {
            order = order.reversed();
        }
        return Arrays.stream(source)
                .map(row -> new ScoredRow(row.clone(), longestRun(row, increasing).length()))
                .sorted(order)
                .map(ScoredRow::values)
                .toArray(int[][]::new);
    }

    public static int[][] jagged(int[][] source, boolean increasing) {
        return Arrays.stream(source)
                .map(
                        row -> {
                            Run run = longestRun(row, increasing);
                            return Arrays.copyOfRange(row, run.start(), run.start() + run.length());
                        })
                .toArray(int[][]::new);
    }
}
