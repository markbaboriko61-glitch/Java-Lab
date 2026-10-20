package ru.labs.lab4task3.model;

public final class Heater extends Appliance {
    private final int targetTemperature;

    public Heater(String id, String name, double powerWatts, int voltage, int targetTemperature) {
        super(id, name, powerWatts, voltage);
        if (targetTemperature < 5 || targetTemperature > 35) {
            throw new IllegalArgumentException("Некорректный параметр targetTemperature");
        }
        this.targetTemperature = targetTemperature;
    }

    public int getTargetTemperature() {
        return targetTemperature;
    }
}
