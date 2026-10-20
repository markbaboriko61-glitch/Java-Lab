package ru.labs.lab3task3;

import java.util.Objects;

public record ComplexFraction(Complex numerator, Complex denominator) {
    public ComplexFraction {
        Objects.requireNonNull(numerator, "Числитель");
        Objects.requireNonNull(denominator, "Знаменатель");
        if (denominator.isZero()) {
            throw new IllegalArgumentException("Знаменатель не может быть нулём");
        }
    }

    public ComplexFraction add(ComplexFraction other) {
        return new ComplexFraction(
                numerator.multiply(other.denominator).add(other.numerator.multiply(denominator)),
                denominator.multiply(other.denominator));
    }

    public Complex value() {
        return numerator.divide(denominator);
    }

    @Override
    public String toString() {
        return numerator + " / " + denominator;
    }
}
