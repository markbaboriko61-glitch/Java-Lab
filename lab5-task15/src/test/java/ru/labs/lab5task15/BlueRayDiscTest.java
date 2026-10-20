package ru.labs.lab5task15;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.Duration;

class BlueRayDiscTest {
    @Test
    void buildsNestedCatalogAndStoresEntries() {
        BlueRayDisc disc = new BlueRayDisc("Disc", 1000);
        BlueRayDisc.Directory films = disc.getRoot().addDirectory("Films");
        BlueRayDisc.Directory science = films.addDirectory("Science");
        BlueRayDisc.Recording recording =
                science.addRecording("Space", 400, Duration.ofMinutes(90));
        assertEquals("/", disc.getRoot().getPath());
        assertEquals("/Films/Science/", science.getPath());
        assertEquals("Science", science.getName());
        assertEquals("/Films/Science/Space", recording.getPath());
        assertEquals("Space", recording.getName());
        assertEquals("Disc", recording.getDiscTitle());
        assertEquals(400, recording.getSizeBytes());
        assertEquals(Duration.ofMinutes(90), recording.getDuration());
        assertEquals(400, disc.getUsedBytes());
        assertEquals(1000, disc.getCapacityBytes());
        assertEquals(1, films.getDirectories().size());
        assertEquals(1, science.getRecordings().size());
        assertTrue(recording.toString().contains("5400"));
        assertThrows(UnsupportedOperationException.class, () -> films.getDirectories().clear());
        assertThrows(UnsupportedOperationException.class, () -> science.getRecordings().clear());
    }

    @Test
    void enforcesCapacityAcrossDifferentDirectoriesAndNoOverflow() {
        BlueRayDisc disc = new BlueRayDisc("Disc", 100);
        disc.getRoot().addRecording("one", 60, Duration.ofSeconds(1));
        BlueRayDisc.Directory sub = disc.getRoot().addDirectory("sub");
        assertThrows(
                IllegalStateException.class,
                () -> sub.addRecording("two", 41, Duration.ofSeconds(1)));
        assertEquals(60, disc.getUsedBytes());
        assertTrue(sub.getRecordings().isEmpty());
        sub.addRecording("two", 40, Duration.ofSeconds(1));
        assertEquals(100, disc.getUsedBytes());
        BlueRayDisc huge = new BlueRayDisc("Huge", Long.MAX_VALUE);
        huge.getRoot().addRecording("huge", Long.MAX_VALUE, Duration.ofSeconds(1));
        assertThrows(
                IllegalStateException.class,
                () -> huge.getRoot().addRecording("extra", 1, Duration.ofSeconds(1)));
    }

    @Test
    void validatesNamesSizesAndDurations() {
        assertThrows(IllegalArgumentException.class, () -> new BlueRayDisc("Disc", 0));
        BlueRayDisc disc = new BlueRayDisc("Disc", 100);
        for (String invalid : new String[] {"", " ", "/", "a/b", "a\\b", ".", ".."}) {
            assertThrows(
                    IllegalArgumentException.class, () -> disc.getRoot().addDirectory(invalid));
        }
        disc.getRoot().addDirectory("x");
        assertThrows(IllegalArgumentException.class, () -> disc.getRoot().addDirectory("x"));
        assertThrows(
                IllegalArgumentException.class,
                () -> disc.getRoot().addRecording("x", 1, Duration.ofSeconds(1)));
        disc.getRoot().addRecording("y", 1, Duration.ofSeconds(1));
        assertThrows(IllegalArgumentException.class, () -> disc.getRoot().addDirectory("y"));
        assertThrows(
                IllegalArgumentException.class,
                () -> disc.getRoot().addRecording("bad", 0, Duration.ofSeconds(1)));
        assertThrows(
                IllegalArgumentException.class,
                () -> disc.getRoot().addRecording("bad", 1, Duration.ZERO));
        assertThrows(
                IllegalArgumentException.class,
                () -> disc.getRoot().addRecording("bad", 1, Duration.ofSeconds(-1)));
    }

    @Test
    void demoRuns() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }
}
