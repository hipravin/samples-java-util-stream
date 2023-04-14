package com.hipravin;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileStatisticReaderTest {
    static Path sampleTiny = Paths.get("src/main/resources/sample-tiny.txt");
    static Path sampleMiddle = Paths.get("src/main/resources/sample-medium.txt");

    static FileStatisticReader fileLines = new FileLines();
    static FileStatisticReader fileLinesWithoutProperClose = new FileLinesWithoutProperClose();
    static FileStatisticReader fileLinesReadAllLines = new FileLinesReadAllLines();
    static FileStatisticReader fileLinesBufferedReader = new FileLinesBufferedReader();

    @Test
    void testActuallyDifferentImplementations() {
        assertNotSame(fileLines.getClass(), fileLinesWithoutProperClose.getClass());
        assertNotSame(fileLines.getClass(), fileLinesReadAllLines.getClass());
        assertNotSame(fileLines.getClass(), fileLinesBufferedReader.getClass());

        assertNotSame(fileLinesWithoutProperClose.getClass(), fileLinesReadAllLines.getClass());
        assertNotSame(fileLinesWithoutProperClose.getClass(), fileLinesBufferedReader.getClass());

        assertNotSame(fileLinesReadAllLines.getClass(), fileLinesBufferedReader.getClass());
    }

    @Test
    void testCorrectTiny() {
        long expected = 2;
        Path path = sampleTiny;

        assertEquals(expected, fileLines.countLines(path));
        assertEquals(expected, fileLinesWithoutProperClose.countLines(path));
        assertEquals(expected, fileLinesReadAllLines.countLines(path));
        assertEquals(expected, fileLinesBufferedReader.countLines(path));
    }

    @Test
    void testCorrectMiddle() {
        long expected = 400;
        Path path = sampleMiddle;

        assertEquals(expected, fileLines.countLines(path));
        assertEquals(expected, fileLinesWithoutProperClose.countLines(path));
        assertEquals(expected, fileLinesReadAllLines.countLines(path));
        assertEquals(expected, fileLinesBufferedReader.countLines(path));
    }
}