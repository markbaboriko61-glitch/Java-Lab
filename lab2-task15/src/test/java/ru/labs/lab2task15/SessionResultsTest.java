package ru.labs.lab2task15;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SessionResultsTest {
    @Test
    void expelsAndSortsWithStableTies() {
        int[][] input = {{100001, 3, 4}, {100002, 5, 2}, {100003, 5, 5}, {100004, 4, 3}};
        int[][] result = SessionResults.expelAndSort(input);
        assertEquals(3, result.length);
        assertEquals(100003, result[0][0]);
        assertEquals(100001, result[1][0]);
        assertEquals(100004, result[2][0]);
        assertEquals(3.5, SessionResults.average(input[0]));
        result[1][1] = 5;
        assertEquals(3, input[0][1]);
    }

    @Test
    void emptyAndAllDebtors() {
        assertEquals(0, SessionResults.expelAndSort(new int[][] {}).length);
        assertEquals(0, SessionResults.expelAndSort(new int[][] {{100001, 2}}).length);
    }

    @Test
    void validatesIdsAndMarks() {
        assertThrows(
                IllegalArgumentException.class,
                () -> SessionResults.validate(new int[][] {{100001, 4}, {100001, 5}}));
        assertThrows(
                IllegalArgumentException.class,
                () -> SessionResults.validate(new int[][] {{1, 4}}));
        assertThrows(
                IllegalArgumentException.class,
                () -> SessionResults.validate(new int[][] {{100001, 6}}));
        assertThrows(
                IllegalArgumentException.class,
                () -> SessionResults.validate(new int[][] {{100001}}));
        assertThrows(
                IllegalArgumentException.class, () -> SessionResults.average(new int[] {100001}));
    }
}
