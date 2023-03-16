package com.hipravin;

import com.hipravin.utils.Preconditions;
import com.hipravin.utils.TextUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFlatMapImplTextStatisticAnalyzer implements TextStatisticAnalyzer {
    @Override
    public Map<String, Long> letterFrequency(Path file, int minWordLength) {
        Preconditions.isPositive(minWordLength);
        Preconditions.fileIsReadable(file);

        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            return letterFrequency(lines, minWordLength);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Map<String, Long> letterFrequency(Stream<String> lines, int minWordLength) {
        Map<String, Long> letterFrequency =
            lines.flatMap(this::words)
                    .filter(w -> w.length() >= minWordLength)
                    .map(String::toLowerCase)
                    .flatMapToInt(String::chars)
                    .mapToObj(i -> String.valueOf((char) i))
                    .collect(Collectors.toMap(i -> i, i -> 1L, Long::sum, TreeMap::new));

        return letterFrequency;
    }

    private Stream<String> words(String line) {
        return Arrays.stream(TextUtils.splitWords(line));
    }
}
