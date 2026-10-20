package ru.labs.lab4task3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ru.labs.lab4task3.creator.*;
import ru.labs.lab4task3.io.ApplianceFileReader;
import ru.labs.lab4task3.model.*;
import ru.labs.lab4task3.service.ApplianceService;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class ApplianceTest {
    private final ApplianceFactory factory = new ApplianceFactory();

    @Test
    void missingBundledResourceIsReported() {
        assertThrows(IOException.class, () -> Main.resourceReader(null));
    }

    @Test
    void hierarchyFactoryAndState() {
        Heater heater = (Heater) factory.create("HEATER;H; Heat ;2000;230;true;24");
        assertEquals("H", heater.getId());
        assertEquals("Heat", heater.getName());
        assertEquals(2000, heater.getPowerWatts());
        assertEquals(230, heater.getVoltage());
        assertTrue(heater.isPluggedIn());
        assertEquals(24, heater.getTargetTemperature());
        heater.unplug();
        assertFalse(heater.isPluggedIn());
        heater.plugIn();
        assertTrue(heater.toString().contains("Heat"));
        Refrigerator fridge = (Refrigerator) factory.create("FRIDGE;F;Fridge;150;220;false;300");
        assertEquals(300, fridge.getVolumeLiters());
        assertFalse(fridge.isPluggedIn());
        WashingMachine washer =
                (WashingMachine) factory.create("WASHER;W;Washer;1800;230;false;7.5");
        assertEquals(7.5, washer.getLoadKilograms());
    }

    @Test
    void rejectsInvalidObjects() {
        for (String line :
                List.of(
                        "HEATER;H;x;1;220;false",
                        "MISSING;X;x;1;220;false;20",
                        "HEATER;H;x;1;220;yes;20",
                        "HEATER;H;x;0;220;false;20",
                        "HEATER;H;x;NaN;220;false;20",
                        "HEATER;H;x;Infinity;220;false;20",
                        "HEATER;H;x;1;0;false;20",
                        "HEATER;;x;1;220;false;20",
                        "HEATER;H;;1;220;false;20",
                        "HEATER;H;x;1;220;false;4",
                        "HEATER;H;x;1;220;false;36",
                        "FRIDGE;F;x;1;220;false;0",
                        "WASHER;W;x;1;220;false;0",
                        "WASHER;W;x;1;220;false;NaN")) {
            assertThrows(IllegalArgumentException.class, () -> factory.create(line), line);
        }
        assertThrows(NullPointerException.class, () -> new Heater(null, "x", 1, 220, 20));
    }

    @Test
    void readsFileSkippingInvalidAndDuplicates() throws IOException {
        String input =
                "# comment\n\nHEATER;H;Heat;2000;230;true;24\n"
                        + "FRIDGE;F;Fridge;150;220;false;300\nHEATER;H;duplicate;100;220;true;20\n"
                        + "invalid\nFRIDGE;F2;bad;abc;220;false;200\n";
        List<Appliance> appliances = new ApplianceFileReader(factory).read(new StringReader(input));
        assertEquals(2, appliances.size());
        assertEquals("F", appliances.get(1).getId());
    }

    @Test
    void readerPropagatesIoFailure() {
        Reader broken =
                new Reader() {
                    @Override
                    public int read(char[] buffer, int offset, int length) throws IOException {
                        throw new IOException("test");
                    }

                    @Override
                    public void close() {}
                };
        assertThrows(IOException.class, () -> new ApplianceFileReader(factory).read(broken));
    }

    @Test
    void businessLogicAndBoundaries() {
        Appliance a = factory.create("HEATER;B;B;2000;230;true;24");
        Appliance b = factory.create("FRIDGE;A;A;150;220;true;300");
        Appliance c = factory.create("WASHER;C;C;1800;230;false;7");
        Appliance d = factory.create("HEATER;A2;A2;2000;240;false;20");
        List<Appliance> original = new ArrayList<>(List.of(a, b, c, d));
        Home home = new HomeCreator().create(original);
        original.clear();
        ApplianceService service = new ApplianceService();
        assertEquals(2150, service.consumedPower(home));
        assertEquals(List.of(b, c, d, a), service.sortByPower(home));
        assertEquals(List.of(a, c), service.findByRange(home, 1800, 2000, 230, 230));
        assertEquals(List.of(), service.findByRange(home, 2001, 3000, 220, 240));
        assertEquals(List.of(), service.findByRange(home, 0, 1000, 230, 240));
        assertEquals(List.of(b), service.findByRange(home, 150, 150, 220, 220));
        assertThrows(UnsupportedOperationException.class, () -> home.getAppliances().clear());
        assertEquals(0, service.consumedPower(new Home(List.of())));
        assertEquals(List.of(), service.sortByPower(new Home(List.of())));
    }

    @Test
    void invalidSearchRanges() {
        Home home = new Home(List.of());
        ApplianceService service = new ApplianceService();
        double[][] ranges = {
            {Double.NaN, 1, 0, 1},
            {0, Double.NaN, 0, 1},
            {-1, 1, 0, 1},
            {2, 1, 0, 1},
            {0, 1, -1, 1},
            {0, 1, 2, 1}
        };
        for (double[] r : ranges) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> service.findByRange(home, r[0], r[1], (int) r[2], (int) r[3]));
        }
    }

    @Test
    void applicationUsesDefaultAndExternalFiles(@TempDir Path dir) throws IOException {
        assertDoesNotThrow(() -> Main.main(new String[0]));
        Path data = dir.resolve("input.txt");
        Files.writeString(data, "FRIDGE;F;Fridge;150;220;true;300");
        assertDoesNotThrow(() -> Main.main(new String[] {data.toString()}));
        assertDoesNotThrow(() -> Main.main(new String[] {dir.resolve("missing.txt").toString()}));
    }
}
