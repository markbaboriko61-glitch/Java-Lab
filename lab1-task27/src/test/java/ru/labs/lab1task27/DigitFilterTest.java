package ru.labs.lab1task27;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DigitFilterTest {
    @Test
    void requiresEachTargetDigitExactlyOnce() {
        assertTrue(DigitFilter.matches(2037, 4));
        assertTrue(DigitFilter.matches(1203755, 7));
        assertFalse(DigitFilter.matches(20377, 5));
        assertFalse(DigitFilter.matches(2307, 5));
        assertFalse(DigitFilter.matches(203, 3));
        assertFalse(DigitFilter.matches(1237, 4));
        assertThrows(IllegalArgumentException.class, () -> DigitFilter.matches(0, 4));
        assertThrows(IllegalArgumentException.class, () -> DigitFilter.matches(2037, 20));
    }
}
