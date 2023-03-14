package com.hipravin;

import java.nio.file.Path;

public interface FileStatisticReader {
    long countLines(Path path);
}
