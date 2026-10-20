package ru.labs.lab4task3.creator;

import ru.labs.lab4task3.model.Appliance;
import ru.labs.lab4task3.model.WashingMachine;

public final class WashingMachineCreator extends ApplianceCreator {
    @Override
    protected Appliance createAppliance(
            String id, String name, double power, int voltage, String specific) {
        return new WashingMachine(id, name, power, voltage, Double.parseDouble(specific));
    }
}
