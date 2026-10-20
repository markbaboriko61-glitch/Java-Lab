package ru.labs.lab4task3.model;

import java.util.List;

public final class Home {
    private final List<Appliance> appliances;

    public Home(List<Appliance> appliances) {
        this.appliances = List.copyOf(appliances);
    }

    public List<Appliance> getAppliances() {
        return appliances;
    }
}
