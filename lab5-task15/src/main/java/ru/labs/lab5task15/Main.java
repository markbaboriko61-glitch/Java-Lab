package ru.labs.lab5task15;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        BlueRayDisc disc = new BlueRayDisc("Медиатека", 25_000_000_000L);
        BlueRayDisc.Directory films = disc.getRoot().addDirectory("Фильмы");
        BlueRayDisc.Directory documentary = films.addDirectory("Документальные");
        documentary.addRecording("Космос.mkv", 2_000_000_000L, Duration.ofMinutes(90));
        documentary.addRecording("Природа.mkv", 1_000_000_000L, Duration.ofMinutes(50));
        BlueRayDisc.Directory music = disc.getRoot().addDirectory("Музыка");
        music.addRecording("Концерт.mp4", 500_000_000L, Duration.ofMinutes(60));
        System.out.println("Диск: " + disc.getTitle());
        print(disc.getRoot(), "");

        Comparator<BlueRayDisc.Recording> byDuration =
                new Comparator<>() {
                    @Override
                    public int compare(BlueRayDisc.Recording first, BlueRayDisc.Recording second) {
                        int result = first.getDuration().compareTo(second.getDuration());
                        return result != 0 ? result : first.getName().compareTo(second.getName());
                    }
                };
        List<BlueRayDisc.Recording> sorted = new ArrayList<>(documentary.getRecordings());
        sorted.sort(byDuration);
        System.out.println("Документальные записи по длительности:");
        sorted.forEach(System.out::println);
        System.out.println(
                "Занято: " + disc.getUsedBytes() + " из " + disc.getCapacityBytes() + " байт");
    }

    private static void print(BlueRayDisc.Directory directory, String indent) {
        System.out.println(indent + directory.getPath());
        directory
                .getRecordings()
                .forEach(recording -> System.out.println(indent + "  " + recording));
        directory.getDirectories().forEach(child -> print(child, indent + "  "));
    }
}
