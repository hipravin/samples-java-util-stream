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
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMapMultiImplTextStatisticAnalyzer implements TextStatisticAnalyzer {
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
        return lines.mapMulti(this::splitWordsAndAcceptAll)
                .filter(w -> w.length() >= minWordLength)
                .map(String::toLowerCase)
                .mapMultiToInt(this::splitCharsAndAcceptAll)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.toMap(i -> i, i -> 1L, Long::sum, TreeMap::new));
    }

    private void splitWordsAndAcceptAll(String line, Consumer<String> wordConsumer) {
        for (String word : TextUtils.splitWords(line)) {
            wordConsumer.accept(word);
        }
    }

    private void splitCharsAndAcceptAll(String line, IntConsumer characterConsumer) {
        line.chars().forEach(characterConsumer);
    }
}
