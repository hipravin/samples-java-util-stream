package com.hipravin;

import java.nio.file.Path;
import java.util.Map;

public interface TextStatisticAnalyzer {
    Map<String, Long> letterFrequency(Path file, int minWordLength);
}
