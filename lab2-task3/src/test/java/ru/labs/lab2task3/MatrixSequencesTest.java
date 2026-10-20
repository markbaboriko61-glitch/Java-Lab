package ru.labs.lab2task3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MatrixSequencesTest {
    @Test
    void findsFirstLongestStrictRun() {
        assertEquals(
                new MatrixSequences.Run(0, 3),
                MatrixSequences.longestRun(new int[] {1, 2, 3, 0, 1, 2}, true));
        assertEquals(
                new MatrixSequences.Run(0, 1),
                MatrixSequences.longestRun(new int[] {2, 2, 2}, true));
        assertEquals(
                new MatrixSequences.Run(1, 3),
                MatrixSequences.longestRun(new int[] {1, 5, 4, 3}, false));
        assertEquals(new MatrixSequences.Run(0, 0), MatrixSequences.longestRun(new int[] {}, true));
    }

    @Test
    void sortsWithoutChangingSourceAndBuildsJaggedFromOriginal() {
        int[][] source = {{1, 2, 3}, {4, 4, 4}, {2, 3, 0}};
        int[][] sorted = MatrixSequences.sortedRows(source, true, true);
        assertArrayEquals(new int[] {4, 4, 4}, sorted[0]);
        assertArrayEquals(new int[] {1, 2, 3}, sorted[2]);
        assertArrayEquals(new int[] {1, 2, 3}, source[0]);
        int[][] jagged = MatrixSequences.jagged(source, true);
        assertArrayEquals(new int[] {1, 2, 3}, jagged[0]);
        assertArrayEquals(new int[] {4}, jagged[1]);
        assertArrayEquals(new int[] {2, 3}, jagged[2]);
        assertArrayEquals(source[0], MatrixSequences.sortedRows(source, true, false)[0]);
    }
}
