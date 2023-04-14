package com.hipravin.benchmark;

import com.hipravin.*;
import org.openjdk.jmh.annotations.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BenchmarkConfig {
    private static final Path resourcesPath = Paths.get("file-lines/src/main/resources");

    private static FileStatisticReader fileLines = new FileLines();
    private static FileStatisticReader fileLinesWithoutProperClose = new FileLinesWithoutProperClose();
    private static FileStatisticReader fileLinesReadAllLines = new FileLinesReadAllLines();
    private static FileStatisticReader fileLinesBufferedReader = new FileLinesBufferedReader();

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Param({"sample-tiny.txt", "sample-medium.txt", "sample-large.txt"})
        public String fileName;

        public Path filePath;

        @Setup(Level.Invocation)
        public void setUp() {
            filePath = resourcesPath.resolve(fileName);
            if (!Files.isRegularFile(filePath)) {
                throw new IllegalStateException("File is not a regular file: " + filePath);
            }
        }
    }

    @Benchmark
    public long benchFileLines(ExecutionPlan plan) {
        return fileLines.countLines(plan.filePath);
    }

    @Benchmark
    public long benchWithoutProperClose(ExecutionPlan plan) {
        return fileLinesWithoutProperClose.countLines(plan.filePath);
    }

    @Benchmark
    public long benchReadAllLines(ExecutionPlan plan) {
        return fileLinesReadAllLines.countLines(plan.filePath);
    }

    @Benchmark
    public long benchBufferedReader(ExecutionPlan plan) {
        return fileLinesBufferedReader.countLines(plan.filePath);
    }
}
