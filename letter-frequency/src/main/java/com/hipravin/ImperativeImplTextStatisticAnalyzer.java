package com.hipravin;

import com.hipravin.utils.Preconditions;
import com.hipravin.utils.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class ImperativeImplTextStatisticAnalyzer implements TextStatisticAnalyzer {
    @Override
    public Map<String, Long> letterFrequency(Path file, int minWordLength) {
        Preconditions.isPositive(minWordLength);
        Preconditions.fileIsReadable(file);

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            return calculateLetterFrequency(reader, minWordLength);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Map<String, Long> calculateLetterFrequency(BufferedReader reader, int minWordLength) throws IOException {
        Map<String, Long> frequency = new TreeMap<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = TextUtils.splitWords(line);

            for (String word : words) {
                if (word.length() >= minWordLength) {
                    String wordLc = word.toLowerCase();
                    wordLc.chars().forEach(c -> {
                        frequency.merge(String.valueOf((char) c), 1L, (a, b) -> a + b);
                    });
                }
            }
        }

        return frequency;
    }
}
