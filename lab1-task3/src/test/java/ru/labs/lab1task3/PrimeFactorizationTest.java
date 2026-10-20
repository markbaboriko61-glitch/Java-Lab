package ru.labs.lab1task3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class PrimeFactorizationTest {
    @Test
    void handlesOnePrimeAndComposite() {
        assertEquals(List.of(), PrimeFactorization.factorize(1));
        assertEquals(List.of(13L), PrimeFactorization.factorize(13));
        assertEquals(List.of(2L, 2L, 2L, 3L, 3L, 5L), PrimeFactorization.factorize(360));
        assertEquals(List.of(2L), PrimeFactorization.factorize(2));
        assertEquals(List.of(97L, 97L), PrimeFactorization.factorize(9409));
    }

    @Test
    void rejectsNonNatural() {
        assertThrows(IllegalArgumentException.class, () -> PrimeFactorization.factorize(0));
        assertThrows(IllegalArgumentException.class, () -> PrimeFactorization.factorize(-1));
    }
}
