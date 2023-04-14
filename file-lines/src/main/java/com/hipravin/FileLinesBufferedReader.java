package com.hipravin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileLinesBufferedReader implements FileStatisticReader{
    @Override
    public long countLines(Path path) {
        try(BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            long counter = 0;
            String line;
            while((line = reader.readLine()) != null) {
                if(!line.isBlank()) {
                    counter++;
                }
            }
            return counter;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
