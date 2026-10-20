package ru.labs.lab4task3.creator;

import ru.labs.lab4task3.model.Appliance;
import ru.labs.lab4task3.model.Heater;

public final class HeaterCreator extends ApplianceCreator {
    @Override
    protected Appliance createAppliance(
            String id, String name, double power, int voltage, String specific) {
        return new Heater(id, name, power, voltage, Integer.parseInt(specific));
    }
}
