package ru.labs.lab4task3.model;

public final class Refrigerator extends Appliance {
    private final int volumeLiters;

    public Refrigerator(String id, String name, double powerWatts, int voltage, int volumeLiters) {
        super(id, name, powerWatts, voltage);
        if (volumeLiters <= 0) {
            throw new IllegalArgumentException("Некорректный параметр volumeLiters");
        }
        this.volumeLiters = volumeLiters;
    }

    public int getVolumeLiters() {
        return volumeLiters;
    }
}
