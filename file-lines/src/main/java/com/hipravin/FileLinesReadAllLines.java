package com.hipravin;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileLinesReadAllLines implements FileStatisticReader{
    @Override
    public long countLines(Path path) {
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8).size();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
