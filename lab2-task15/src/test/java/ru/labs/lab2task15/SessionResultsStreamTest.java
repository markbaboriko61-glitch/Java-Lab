package ru.labs.lab2task15;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

class SessionResultsStreamTest {
    @Test
    void expelsSortsStablyAndExcludesIdFromAverage() {
        int[][] source = {{100001, 3, 4}, {100002, 5, 2}, {100003, 5, 5}, {100004, 4, 3}};
        int[][] result = SessionResultsStream.expelAndSort(source);
        assertArrayEquals(new int[] {100003, 5, 5}, result[0]);
        assertArrayEquals(new int[] {100001, 3, 4}, result[1]);
        assertArrayEquals(new int[] {100004, 4, 3}, result[2]);
        assertEquals(3.5, SessionResultsStream.average(source[0]));
        assertTrue(SessionResultsStream.isDebtor(source[1]));
        assertFalse(SessionResultsStream.isDebtor(source[0]));
        result[1][1] = 5;
        assertEquals(3, source[0][1]);
    }

    @Test
    void supportsEmptyAllDebtorsAndSingleSubject() {
        assertEquals(0, SessionResultsStream.expelAndSort(new int[0][]).length);
        assertEquals(
                0,
                SessionResultsStream.expelAndSort(new int[][] {{100001, 2}, {100002, 2}}).length);
        assertTrue(
                Arrays.deepEquals(
                        new int[][] {{100001, 5}},
                        SessionResultsStream.expelAndSort(new int[][] {{100001, 5}})));
    }

    @Test
    void rejectsInvalidIdsAndDuplicates() {
        for (int[][] invalid :
                new int[][][] {{{1, 4}}, {{1000000, 4}}, {{100001, 4}, {100001, 5}}}) {
            assertThrows(
                    IllegalArgumentException.class, () -> SessionResultsStream.validate(invalid));
        }
    }

    @Test
    void rejectsInvalidMarksAndShape() {
        for (int[][] invalid :
                new int[][][] {
                    {{100001}}, {{100001, 6}}, {{100001, 1}}, {{100001, 4}, {100002, 3, 5}}
                }) {
            assertThrows(
                    IllegalArgumentException.class, () -> SessionResultsStream.validate(invalid));
        }
        assertThrows(
                IllegalArgumentException.class,
                () -> SessionResultsStream.average(new int[] {100001}));
    }

    @Test
    void matchesOrdinaryVersionOnRandomSessions() {
        Random random = new Random(215);
        for (int iteration = 0; iteration < 200; iteration++) {
            int rows = random.nextInt(30);
            int columns = 2 + random.nextInt(10);
            int[][] matrix = new int[rows][columns];
            for (int i = 0; i < rows; i++) {
                matrix[i][0] = 100000 + i;
                for (int j = 1; j < columns; j++) {
                    matrix[i][j] = 2 + random.nextInt(4);
                }
                assertEquals(
                        SessionResults.average(matrix[i]), SessionResultsStream.average(matrix[i]));
                assertEquals(
                        SessionResults.isDebtor(matrix[i]),
                        SessionResultsStream.isDebtor(matrix[i]));
            }
            assertTrue(
                    Arrays.deepEquals(
                            SessionResults.expelAndSort(matrix),
                            SessionResultsStream.expelAndSort(matrix)));
        }
    }
}
