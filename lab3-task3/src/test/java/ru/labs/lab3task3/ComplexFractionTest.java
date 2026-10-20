package ru.labs.lab3task3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexFractionTest {
    private ComplexFraction fraction(double real) {
        return new ComplexFraction(new Complex(real, 0), new Complex(1, 0));
    }

    @Test
    void complexArithmetic() {
        assertEquals(new Complex(4, 6), new Complex(1, 2).add(new Complex(3, 4)));
        assertEquals(new Complex(-5, 10), new Complex(1, 2).multiply(new Complex(3, 4)));
        Complex result = new Complex(1, 2).divide(new Complex(3, 4));
        assertEquals(0.44, result.real(), 1e-12);
        assertEquals(0.08, result.imaginary(), 1e-12);
        assertEquals(new Complex(1, 1), new Complex(2, 2).divide(new Complex(2, 0)));
    }

    @Test
    void addsFractionsAndPreservesOddAndUnpairedElements() {
        ComplexFraction[] array = {fraction(1), fraction(2), fraction(3), fraction(4), fraction(5)};
        FractionArrayService.addNextToEvenIndices(array);
        assertEquals(3, array[0].value().real());
        assertEquals(2, array[1].value().real());
        assertEquals(7, array[2].value().real());
        assertEquals(4, array[3].value().real());
        assertEquals(5, array[4].value().real());
        ComplexFraction a = new ComplexFraction(new Complex(1, 1), new Complex(2, 0));
        ComplexFraction b = new ComplexFraction(new Complex(1, -1), new Complex(2, 0));
        assertEquals(new Complex(1, 0), a.add(b).value());
        FractionArrayService.addNextToEvenIndices(new ComplexFraction[0]);
    }

    @Test
    void validates() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ComplexFraction(new Complex(1, 0), new Complex(0, 0)));
        assertThrows(IllegalArgumentException.class, () -> new Complex(Double.NaN, 0));
        assertThrows(
                IllegalArgumentException.class, () -> new Complex(1, 0).divide(new Complex(0, 0)));
        assertThrows(
                NullPointerException.class,
                () -> FractionArrayService.addNextToEvenIndices(new ComplexFraction[] {null}));
    }
}
