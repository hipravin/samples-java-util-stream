package com.hipravin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.stream.Stream;

public class FileLines implements FileStatisticReader{
    @Override
    public long countLines(Path path) {
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public static long countLinesBufferedReader(Path path) { //PT33.1455882S
        try(BufferedReader reader = new BufferedReader(new FileReader(path.toFile(), StandardCharsets.UTF_8))) {
            String line;
            long counter = 0;
            while((line = reader.readLine()) != null) {
                counter ++;
            }
            return counter;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public static long countLinesClose(Path path) { //PT13.3115305S
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public static long countReadAllLines(Path path) { //PT13.3115305S
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8).size();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void repeatWithProfiling(Runnable action, int times, String logDescription) {
        long start = System.nanoTime();
        for (int i = 0; i < times; i++) {
            action.run();
        }
        System.out.println(logDescription + ", " + times + " elapsed: " + Duration.ofNanos(System.nanoTime() - start));
    }
}
