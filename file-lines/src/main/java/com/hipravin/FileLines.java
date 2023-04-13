package com.hipravin;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileLines implements FileStatisticReader {
    @Override
    public long countLines(Path path) {
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
