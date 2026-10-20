package ru.labs.lab3task3;

import java.util.Objects;

public final class FractionArrayService {
    private FractionArrayService() {}

    public static void addNextToEvenIndices(ComplexFraction[] fractions) {
        Objects.requireNonNull(fractions, "Массив");
        for (ComplexFraction fraction : fractions) {
            Objects.requireNonNull(fraction, "Элемент массива");
        }
        for (int i = 0; i + 1 < fractions.length; i += 2) {
            fractions[i] = fractions[i].add(fractions[i + 1]);
        }
    }
}
