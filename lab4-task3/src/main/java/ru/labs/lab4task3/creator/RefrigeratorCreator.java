package ru.labs.lab4task3.creator;

import ru.labs.lab4task3.model.Appliance;
import ru.labs.lab4task3.model.Refrigerator;

public final class RefrigeratorCreator extends ApplianceCreator {
    @Override
    protected Appliance createAppliance(
            String id, String name, double power, int voltage, String specific) {
        return new Refrigerator(id, name, power, voltage, Integer.parseInt(specific));
    }
}
