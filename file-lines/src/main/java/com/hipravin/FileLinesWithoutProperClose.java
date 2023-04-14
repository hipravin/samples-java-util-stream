package com.hipravin;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileLinesWithoutProperClose implements FileStatisticReader{
    @Override
    public long countLines(Path path) {
        try {
            return Files.lines(path, StandardCharsets.UTF_8)
                    .filter(s -> !s.isBlank())
                    .count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
