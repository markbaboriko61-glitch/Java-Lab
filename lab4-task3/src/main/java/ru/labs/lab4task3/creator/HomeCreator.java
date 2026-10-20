package ru.labs.lab4task3.creator;

import ru.labs.lab4task3.model.Appliance;
import ru.labs.lab4task3.model.Home;

import java.util.List;

public final class HomeCreator {
    public Home create(List<Appliance> appliances) {
        return new Home(appliances);
    }
}
