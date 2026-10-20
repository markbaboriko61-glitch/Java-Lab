package ru.labs.lab4task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.labs.lab4task3.creator.ApplianceFactory;
import ru.labs.lab4task3.creator.HomeCreator;
import ru.labs.lab4task3.io.ApplianceFileReader;
import ru.labs.lab4task3.model.Home;
import ru.labs.lab4task3.service.ApplianceService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private Main() {}

    public static void main(String[] args) {

        try (Reader reader = openReader(args)) {
            Home home =
                    new HomeCreator()
                            .create(new ApplianceFileReader(new ApplianceFactory()).read(reader));
            ApplianceService service = new ApplianceService();
            LOGGER.info(
                    "Суммарная мощность включённых приборов: {} Вт", service.consumedPower(home));
            LOGGER.info("Приборы по возрастанию мощности: {}", service.sortByPower(home));
            LOGGER.info(
                    "Поиск: 100..2200 Вт, 220..240 В: {}",
                    service.findByRange(home, 100, 2200, 220, 240));
        } catch (IOException exception) {
            LOGGER.error("Не удалось прочитать исходный файл", exception);
        }
    }

    private static Reader openReader(String[] args) throws IOException {
        if (args.length > 0) {
            return Files.newBufferedReader(Path.of(args[0]), StandardCharsets.UTF_8);
        }
        return resourceReader(Main.class.getResourceAsStream("/appliances.txt"));
    }

    static Reader resourceReader(InputStream stream) throws IOException {
        if (stream == null) {
            throw new IOException("В ресурсах нет appliances.txt");
        }
        return new InputStreamReader(stream, StandardCharsets.UTF_8);
    }
}
