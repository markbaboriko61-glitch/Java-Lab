package ru.labs.lab1task15;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DigitAnalyzerTest {
    @Test
    void countsMaximumOnly() {
        assertEquals(new DigitAnalyzer.Result(9, 3), DigitAnalyzer.analyze(90919));
        assertEquals(new DigitAnalyzer.Result(1, 1), DigitAnalyzer.analyze(1000));
        assertEquals(new DigitAnalyzer.Result(7, 4), DigitAnalyzer.analyze(7777));
        assertEquals(new DigitAnalyzer.Result(5, 1), DigitAnalyzer.analyze(5));
    }

    @Test
    void rejectsZero() {
        assertThrows(IllegalArgumentException.class, () -> DigitAnalyzer.analyze(0));
    }
}
