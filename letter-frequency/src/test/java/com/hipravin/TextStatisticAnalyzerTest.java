package com.hipravin;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextStatisticAnalyzerTest {
    static Path sample1Path;
    static Path vmPath;
    static TextStatisticAnalyzer streamFlatMapImpl = new StreamFlatMapImplTextStatisticAnalyzer();
    static TextStatisticAnalyzer streamMapMultiImpl = new StreamMapMultiImplTextStatisticAnalyzer();
    static TextStatisticAnalyzer imperativeImpl = new ImperativeImplTextStatisticAnalyzer();
    static int MIN_WORD_LENGTH = 3;

    @BeforeAll
    static void setUp() throws URISyntaxException {
        sample1Path = Path.of(ClassLoader.getSystemResource("sample1.txt").toURI());
        vmPath = Path.of(ClassLoader.getSystemResource("voyna-i-mir-tom-1.txt").toURI());
    }

    @Test
    void testSameResult() {
        Map<String, Long> frequencyFlatMap = streamFlatMapImpl.letterFrequency(sample1Path, MIN_WORD_LENGTH);
        Map<String, Long> frequencyMapMulti = streamMapMultiImpl.letterFrequency(sample1Path, MIN_WORD_LENGTH);
        Map<String, Long> imperative = imperativeImpl.letterFrequency(sample1Path, MIN_WORD_LENGTH);

        assertEquals(frequencyFlatMap, frequencyMapMulti);
        assertEquals(frequencyFlatMap, imperative);
    }

    @Test
    void testStreamFlatMapImpl() {
        Map<String, Long> frequency = streamFlatMapImpl.letterFrequency(sample1Path, MIN_WORD_LENGTH);
        assertEquals(27, frequency.size());
//        frequency.forEach((s, f) -> System.out.printf("%s: %d%n", s, f));
    }

    @Test
    void testImperativeImpl() {
        Map<String, Long> frequency = imperativeImpl.letterFrequency(sample1Path, MIN_WORD_LENGTH);
        assertEquals(27, frequency.size());
//        frequency.forEach((s, f) -> System.out.printf("%s: %d%n", s, f));
    }
}