package com.hipravin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class FileLinesBufferedReader implements FileStatisticReader{
    @Override
    public long countLines(Path path) {
        try(BufferedReader reader = new BufferedReader(new FileReader(path.toFile(), StandardCharsets.UTF_8))) {
            long counter = 0;
            while(reader.readLine() != null) {
                counter ++;
            }
            return counter;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
