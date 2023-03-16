package com.hipravin.benchmark;

import com.hipravin.ImperativeImplTextStatisticAnalyzer;
import com.hipravin.StreamFlatMapImplTextStatisticAnalyzer;
import com.hipravin.StreamMapMultiImplTextStatisticAnalyzer;
import com.hipravin.TextStatisticAnalyzer;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

public class BenchmarkConfig {
    static TextStatisticAnalyzer imperative = new ImperativeImplTextStatisticAnalyzer();
    static TextStatisticAnalyzer streamFlatMap = new StreamFlatMapImplTextStatisticAnalyzer();
    static TextStatisticAnalyzer streamMapMulti = new StreamMapMultiImplTextStatisticAnalyzer();

    private static final int BENCHMARK_ITERATION_SECONDS = 5;
    private static final int BENCHMARK_WARMUP_SECONDS = 2;

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Param({ "voyna-i-mir-tom-1.txt"})
        public String fileName;

        @Param({ "3"})
        public int minWordLength;

        public Path filePath;

        @Setup(Level.Invocation)
        public void setUp() throws URISyntaxException {
            filePath = Path.of(ClassLoader.getSystemResource(fileName).toURI());
            if(!Files.isRegularFile(filePath)) {
                throw new IllegalStateException("File is not a regular file: " + filePath);
            }
        }
    }


    @Benchmark
    @Fork(value = 3)
    @Measurement(iterations = 3, time = BENCHMARK_ITERATION_SECONDS)
    @Warmup(iterations = 2, time = BENCHMARK_WARMUP_SECONDS)
    public long readFileFully(ExecutionPlan plan) {
        try(Stream<String> lines = Files.lines(plan.filePath)) {
            return lines.count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Benchmark
    @Fork(value = 3)
    @Measurement(iterations = 3, time = BENCHMARK_ITERATION_SECONDS)
    @Warmup(iterations = 2, time = BENCHMARK_WARMUP_SECONDS)
    public Map<?,?> imperative(ExecutionPlan plan) {
        return imperative.letterFrequency(plan.filePath, plan.minWordLength);
    }

    @Benchmark
    @Fork(value = 3)
    @Measurement(iterations = 3, time = BENCHMARK_ITERATION_SECONDS)
    @Warmup(iterations = 2, time = BENCHMARK_WARMUP_SECONDS)
    public Map<?,?> streamFlatMap(ExecutionPlan plan) {
        return streamFlatMap.letterFrequency(plan.filePath, plan.minWordLength);
    }

    @Benchmark
    @Fork(value = 3)
    @Measurement(iterations = 3, time = BENCHMARK_ITERATION_SECONDS)
    @Warmup(iterations = 2, time = BENCHMARK_WARMUP_SECONDS)
    public Map<?,?> streamMapMulti(ExecutionPlan plan) {
        return streamMapMulti.letterFrequency(plan.filePath, plan.minWordLength);
    }
}
