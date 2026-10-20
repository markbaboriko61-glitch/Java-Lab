package ru.labs.lab3task3;

public record Complex(double real, double imaginary) {
    public Complex {
        if (!Double.isFinite(real) || !Double.isFinite(imaginary)) {
            throw new IllegalArgumentException("Части комплексного числа должны быть конечными");
        }
    }

    public boolean isZero() {
        return real == 0.0 && imaginary == 0.0;
    }

    public Complex add(Complex other) {
        return new Complex(real + other.real, imaginary + other.imaginary);
    }

    public Complex multiply(Complex other) {
        return new Complex(
                real * other.real - imaginary * other.imaginary,
                real * other.imaginary + imaginary * other.real);
    }

    public Complex divide(Complex other) {
        if (other.isZero()) {
            throw new IllegalArgumentException("Деление на комплексный ноль");
        }

        if (Math.abs(other.real) >= Math.abs(other.imaginary)) {
            double ratio = other.imaginary / other.real;
            double denominator = other.real + other.imaginary * ratio;
            return new Complex(
                    (real + imaginary * ratio) / denominator,
                    (imaginary - real * ratio) / denominator);
        }
        double ratio = other.real / other.imaginary;
        double denominator = other.imaginary + other.real * ratio;
        return new Complex(
                (real * ratio + imaginary) / denominator, (imaginary * ratio - real) / denominator);
    }

    @Override
    public String toString() {
        return "(" + real + (imaginary < 0 ? " - " : " + ") + Math.abs(imaginary) + "i)";
    }
}
