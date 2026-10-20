package ru.labs.lab4task3.model;

public final class WashingMachine extends Appliance {
    private final double loadKilograms;

    public WashingMachine(
            String id, String name, double powerWatts, int voltage, double loadKilograms) {
        super(id, name, powerWatts, voltage);
        if (!Double.isFinite(loadKilograms) || loadKilograms <= 0) {
            throw new IllegalArgumentException("Некорректный параметр loadKilograms");
        }
        this.loadKilograms = loadKilograms;
    }

    public double getLoadKilograms() {
        return loadKilograms;
    }
}
