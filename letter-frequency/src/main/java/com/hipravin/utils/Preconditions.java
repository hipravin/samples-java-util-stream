package com.hipravin.utils;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Preconditions {
    private Preconditions() {}

    public static void isPositive(int value) {
        if(value <= 0) {
            throw new IllegalArgumentException("Value should be positive: " + value);
        }
    }

    public static void fileIsReadable(Path file) {
        if(!Files.isReadable(file)) {
            throw new IllegalArgumentException("File is not readable: " + file);
        }
    }
}
