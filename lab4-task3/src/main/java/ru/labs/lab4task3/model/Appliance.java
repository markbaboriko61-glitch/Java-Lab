package ru.labs.lab4task3.model;

import java.util.Objects;

public abstract class Appliance {
    private final String id;
    private final String name;
    private final double powerWatts;
    private final int voltage;
    private boolean pluggedIn;

    protected Appliance(String id, String name, double powerWatts, int voltage) {
        this.id = requireText(id);
        this.name = requireText(name);
        if (!Double.isFinite(powerWatts) || powerWatts <= 0 || voltage <= 0) {
            throw new IllegalArgumentException("Мощность и напряжение должны быть положительными");
        }
        this.powerWatts = powerWatts;
        this.voltage = voltage;
    }

    private static String requireText(String value) {
        if (Objects.requireNonNull(value).isBlank()) {
            throw new IllegalArgumentException("Пустое имя или ID");
        }
        return value;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPowerWatts() {
        return powerWatts;
    }

    public int getVoltage() {
        return voltage;
    }

    public boolean isPluggedIn() {
        return pluggedIn;
    }

    public void plugIn() {
        pluggedIn = true;
    }

    public void unplug() {
        pluggedIn = false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " ["
                + id
                + ", "
                + name
                + ", "
                + powerWatts
                + " Вт, "
                + voltage
                + " В, включён="
                + pluggedIn
                + "]";
    }
}
