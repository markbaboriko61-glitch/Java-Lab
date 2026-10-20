package ru.labs.lab4task3.creator;

import ru.labs.lab4task3.model.Appliance;

public abstract class ApplianceCreator {
    public final Appliance create(String[] fields) {
        if (fields.length != 7) {
            throw new IllegalArgumentException("В записи должно быть 7 полей");
        }
        if (!fields[5].equals("true") && !fields[5].equals("false")) {
            throw new IllegalArgumentException("Признак включения: true или false");
        }
        Appliance appliance =
                createAppliance(
                        fields[1],
                        fields[2],
                        Double.parseDouble(fields[3]),
                        Integer.parseInt(fields[4]),
                        fields[6]);
        if (Boolean.parseBoolean(fields[5])) {
            appliance.plugIn();
        }
        return appliance;
    }

    protected abstract Appliance createAppliance(
            String id, String name, double power, int voltage, String specific);
}
