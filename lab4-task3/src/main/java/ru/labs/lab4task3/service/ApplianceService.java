package ru.labs.lab4task3.service;

import ru.labs.lab4task3.model.Appliance;
import ru.labs.lab4task3.model.Home;

import java.util.Comparator;
import java.util.List;

public final class ApplianceService {
    public double consumedPower(Home home) {
        return home.getAppliances().stream()
                .filter(Appliance::isPluggedIn)
                .mapToDouble(Appliance::getPowerWatts)
                .sum();
    }

    public List<Appliance> sortByPower(Home home) {
        Comparator<Appliance> comparator =
                Comparator.comparingDouble(Appliance::getPowerWatts)
                        .thenComparing(Appliance::getId);
        return home.getAppliances().stream().sorted(comparator).toList();
    }

    public List<Appliance> findByRange(
            Home home, double minPower, double maxPower, int minVoltage, int maxVoltage) {
        if (!Double.isFinite(minPower)
                || !Double.isFinite(maxPower)
                || minPower < 0
                || minPower > maxPower
                || minVoltage < 0
                || minVoltage > maxVoltage) {
            throw new IllegalArgumentException("Неверные границы диапазона");
        }
        return home.getAppliances().stream()
                .filter(
                        a ->
                                a.getPowerWatts() >= minPower
                                        && a.getPowerWatts() <= maxPower
                                        && a.getVoltage() >= minVoltage
                                        && a.getVoltage() <= maxVoltage)
                .toList();
    }
}
