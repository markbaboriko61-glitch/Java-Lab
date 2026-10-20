package ru.labs.lab4task3.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.labs.lab4task3.creator.ApplianceFactory;
import ru.labs.lab4task3.model.Appliance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ApplianceFileReader {
    private static final Logger LOGGER = LogManager.getLogger(ApplianceFileReader.class);
    private final ApplianceFactory factory;

    public ApplianceFileReader(ApplianceFactory factory) {
        this.factory = factory;
    }

    public List<Appliance> read(Reader source) throws IOException {
        BufferedReader reader = new BufferedReader(source);
        List<Appliance> appliances = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            if (line.isBlank() || line.stripLeading().startsWith("#")) {
                continue;
            }
            try {
                Appliance appliance = factory.create(line);
                if (!ids.add(appliance.getId())) {
                    LOGGER.warn("Строка {} пропущена: повтор ID {}", lineNumber, appliance.getId());
                    continue;
                }
                appliances.add(appliance);
                LOGGER.debug("Создан прибор: {}", appliance);
            } catch (NumberFormatException exception) {
                LOGGER.error("Строка {} пропущена: неверный формат числа", lineNumber);
            } catch (IllegalArgumentException exception) {
                LOGGER.warn("Строка {} пропущена: {}", lineNumber, exception.getMessage());
            }
        }
        LOGGER.info("Загружено приборов: {}", appliances.size());
        return appliances;
    }
}
