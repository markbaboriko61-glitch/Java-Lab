package ru.labs.lab2task3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

class MatrixSequencesStreamTest {
    @Test
    void handlesEmptySingleEqualAndBoundaryValues() {
        assertEquals(
                new MatrixSequences.Run(0, 0), MatrixSequencesStream.longestRun(new int[0], true));
        assertEquals(
                new MatrixSequences.Run(0, 1),
                MatrixSequencesStream.longestRun(new int[] {7}, false));
        assertEquals(
                new MatrixSequences.Run(0, 1),
                MatrixSequencesStream.longestRun(new int[] {2, 2, 2}, true));
        assertEquals(
                new MatrixSequences.Run(0, 2),
                MatrixSequencesStream.longestRun(
                        new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}, true));
        assertEquals(
                new MatrixSequences.Run(0, 2),
                MatrixSequencesStream.longestRun(
                        new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE}, false));
    }

    @Test
    void picksFirstLongestRun() {
        assertEquals(
                new MatrixSequences.Run(0, 3),
                MatrixSequencesStream.longestRun(new int[] {1, 2, 3, 0, 1, 2}, true));
        assertEquals(
                new MatrixSequences.Run(1, 3),
                MatrixSequencesStream.longestRun(new int[] {1, 5, 4, 3}, false));
    }

    @Test
    void preservesStableTiesAndSource() {
        int[][] source = {{1, 2, 3}, {4, 4, 4}, {2, 3, 0}, {8, 8, 8}};
        int[][] result = MatrixSequencesStream.sortedRows(source, true, true);
        assertArrayEquals(new int[] {4, 4, 4}, result[0]);
        assertArrayEquals(new int[] {8, 8, 8}, result[1]);
        assertArrayEquals(new int[] {1, 2, 3}, source[0]);
        result[0][0] = 100;
        assertEquals(4, source[1][0]);
    }

    @Test
    void buildsJaggedFromOriginalAndSupportsEmptyRows() {
        int[][] source = {{1, 2, 3}, {}, {4, 4, 4}, {2, 3, 0}};
        int[][] result = MatrixSequencesStream.jagged(source, true);
        assertTrue(Arrays.deepEquals(new int[][] {{1, 2, 3}, {}, {4}, {2, 3}}, result));
        assertEquals(0, MatrixSequencesStream.sortedRows(new int[0][], true, true).length);
        result[0][0] = 50;
        assertEquals(1, source[0][0]);
    }

    @Test
    void matchesOrdinaryVersionOnRandomMatricesForAllDirections() {
        Random random = new Random(20315);
        for (int iteration = 0; iteration < 200; iteration++) {
            int[][] matrix = new int[random.nextInt(15)][];
            for (int row = 0; row < matrix.length; row++) {
                matrix[row] = random.ints(random.nextInt(15), -10, 11).toArray();
            }
            for (boolean increasing : new boolean[] {true, false}) {
                for (int[] row : matrix) {
                    assertEquals(
                            MatrixSequences.longestRun(row, increasing),
                            MatrixSequencesStream.longestRun(row, increasing));
                }
                assertTrue(
                        Arrays.deepEquals(
                                MatrixSequences.jagged(matrix, increasing),
                                MatrixSequencesStream.jagged(matrix, increasing)));
                for (boolean ascending : new boolean[] {true, false}) {
                    assertTrue(
                            Arrays.deepEquals(
                                    MatrixSequences.sortedRows(matrix, increasing, ascending),
                                    MatrixSequencesStream.sortedRows(
                                            matrix, increasing, ascending)));
                }
            }
        }
    }
}
