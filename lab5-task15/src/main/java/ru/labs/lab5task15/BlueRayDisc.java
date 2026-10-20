package ru.labs.lab5task15;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BlueRayDisc {
    private final String title;
    private final long capacityBytes;
    private long usedBytes;
    private final Directory root;

    public BlueRayDisc(String title, long capacityBytes) {
        this.title = checkedName(title);
        if (capacityBytes <= 0) {
            throw new IllegalArgumentException("Ёмкость должна быть положительной");
        }
        this.capacityBytes = capacityBytes;
        this.root = new Directory("", null);
    }

    private static String checkedName(String name) {
        if (Objects.requireNonNull(name).isBlank()
                || name.contains("/")
                || name.contains("\\")
                || name.equals(".")
                || name.equals("..")) {
            throw new IllegalArgumentException("Недопустимое имя");
        }
        return name;
    }

    public String getTitle() {
        return title;
    }

    public long getCapacityBytes() {
        return capacityBytes;
    }

    public long getUsedBytes() {
        return usedBytes;
    }

    public Directory getRoot() {
        return root;
    }

    public final class Directory {
        private final String name;
        private final Directory parent;
        private final List<Directory> directories = new ArrayList<>();
        private final List<Recording> recordings = new ArrayList<>();

        private Directory(String name, Directory parent) {
            this.name = name;
            this.parent = parent;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return parent == null ? "/" : parent.getPath() + name + "/";
        }

        public List<Directory> getDirectories() {
            return List.copyOf(directories);
        }

        public List<Recording> getRecordings() {
            return List.copyOf(recordings);
        }

        public Directory addDirectory(String name) {
            ensureUnique(checkedName(name));
            Directory directory = new Directory(name, this);
            directories.add(directory);
            return directory;
        }

        public Recording addRecording(String name, long sizeBytes, Duration duration) {
            ensureUnique(checkedName(name));
            if (sizeBytes <= 0
                    || Objects.requireNonNull(duration).isNegative()
                    || duration.isZero()) {
                throw new IllegalArgumentException(
                        "Размер и длительность должны быть положительными");
            }

            if (sizeBytes > capacityBytes - usedBytes) {
                throw new IllegalStateException("На диске недостаточно места");
            }
            Recording recording = new Recording(name, sizeBytes, duration, this);
            recordings.add(recording);
            usedBytes += sizeBytes;
            return recording;
        }

        private void ensureUnique(String name) {
            if (directories.stream().anyMatch(d -> d.name.equals(name))
                    || recordings.stream().anyMatch(r -> r.name.equals(name))) {
                throw new IllegalArgumentException("Имя уже занято в каталоге: " + name);
            }
        }
    }

    public final class Recording {
        private final String name;
        private final long sizeBytes;
        private final Duration duration;
        private final Directory directory;

        private Recording(String name, long sizeBytes, Duration duration, Directory directory) {
            this.name = name;
            this.sizeBytes = sizeBytes;
            this.duration = duration;
            this.directory = directory;
        }

        public String getName() {
            return name;
        }

        public long getSizeBytes() {
            return sizeBytes;
        }

        public Duration getDuration() {
            return duration;
        }

        public String getPath() {
            return directory.getPath() + name;
        }

        public String getDiscTitle() {
            return BlueRayDisc.this.title;
        }

        @Override
        public String toString() {
            return getPath() + " (" + sizeBytes + " байт, " + duration.toSeconds() + " с)";
        }
    }
}
