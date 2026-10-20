package ru.labs.lab4task3.creator;

import ru.labs.lab4task3.model.Appliance;

import java.util.Arrays;
import java.util.Map;

public final class ApplianceFactory {
    private final Map<String, ApplianceCreator> creators =
            Map.of(
                    "HEATER",
                    new HeaterCreator(),
                    "FRIDGE",
                    new RefrigeratorCreator(),
                    "WASHER",
                    new WashingMachineCreator());

    public Appliance create(String line) {
        String[] fields =
                Arrays.stream(line.split(";", -1)).map(String::trim).toArray(String[]::new);
        ApplianceCreator creator = creators.get(fields[0]);
        if (creator == null) {
            throw new IllegalArgumentException("Неизвестный тип: " + fields[0]);
        }
        return creator.create(fields);
    }
}
