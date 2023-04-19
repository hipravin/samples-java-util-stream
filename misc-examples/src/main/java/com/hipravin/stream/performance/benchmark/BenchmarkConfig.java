package com.hipravin.stream.performance.benchmark;

import org.openjdk.jmh.annotations.*;

import static com.hipravin.stream.performance.Sum.*;

public class BenchmarkConfig {
    private static final long sumToInclusive = 100_000_000;

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Setup(Level.Invocation)
        public void setUp() {
            long expected = sumBigInteger(sumToInclusive);
            assertEquals(expected, sumPrimitiveStream(sumToInclusive));
            assertEquals(expected, sumPrimitiveCycle(sumToInclusive));
            assertEquals(expected, sumBoxedCycle(sumToInclusive));
            assertEquals(expected, sumAtomicLong(sumToInclusive));
            assertEquals(expected, sumBoxedStream(sumToInclusive));
            assertEquals(expected, sumPrimitiveFlatMap(sumToInclusive));
            assertEquals(expected, sumPrimitiveMapMulti(sumToInclusive));
            assertEquals(expected, sumBoxedStreamParallel(sumToInclusive));
            assertEquals(expected, sumPrimitiveStreamParallel(sumToInclusive));
            assertEquals(expected, sumPrimitiveFlatMapParallel(sumToInclusive));
            assertEquals(sumPrimitiveCycleWithIf(sumToInclusive), sumPrimitiveStreamWithFilter(sumToInclusive));
        }
    }

    @Benchmark
    public long bench_sumPrimitiveStream() {
        return sumPrimitiveStream(sumToInclusive);
    }

    @Benchmark
    public long bench_sumPrimitiveParallelStream() {
        return sumPrimitiveStreamParallel(sumToInclusive);
    }

    @Benchmark
    public long bench_sumPrimitiveCycle() {
        return sumPrimitiveCycle(sumToInclusive);
    }

    @Benchmark
    public long bench_sumBoxedCycle() {
        return sumBoxedCycle(sumToInclusive);
    }

    @Benchmark
    public long bench_sumAtomicLong() {
        return sumAtomicLong(sumToInclusive);
    }

    @Benchmark
    public long bench_sumBoxedStream() {
        return sumBoxedStream(sumToInclusive);
    }

    @Benchmark
    public long bench_sumBoxedParallel() {
        return sumBoxedStreamParallel(sumToInclusive);
    }

    @Benchmark
    public long bench_sumBigInteger() {
        return sumBigInteger(sumToInclusive);
    }

    @Benchmark
    public long bench_sumPrimitiveFlatMap() {
        return sumPrimitiveFlatMap(sumToInclusive);
    }

    @Benchmark
    public long bench_sumPrimitiveFlatMapParallel() {
        return sumPrimitiveFlatMapParallel(sumToInclusive);
    }

    @Benchmark
    public long bench_sumPrimitiveMapMulti() {
        return sumPrimitiveMapMulti(sumToInclusive);
    }

    @Benchmark
    public long bench_sumPrimitiveStreamWithFilter() {
        return sumPrimitiveStreamWithFilter(sumToInclusive);
    }

    @Benchmark
    public long bench_sumPrimitiveCycleWithIf() {
        return sumPrimitiveCycleWithIf(sumToInclusive);
    }

    static void assertEquals(long l1, long l2) {
        if (l1 != l2) {
            throw new IllegalStateException("different: " + l1 + " != " + l2);
        }
    }
}
//sample run:

//Benchmark                                             Mode  Cnt   Score   Error  Units
//BenchmarkConfig.bench_sumPrimitiveStream             thrpt    5  32,616 ± 1,055  ops/s
//BenchmarkConfig.bench_sumAtomicLong                  thrpt    5   1,869 ± 0,068  ops/s
//BenchmarkConfig.bench_sumBigInteger                  thrpt    5   0,360 ± 0,083  ops/s
//BenchmarkConfig.bench_sumBoxedCycle                  thrpt    5   1,296 ± 0,080  ops/s
//BenchmarkConfig.bench_sumBoxedStream                 thrpt    5   0,879 ± 0,162  ops/s
//BenchmarkConfig.bench_sumPrimitiveCycle              thrpt    5  32,859 ± 1,880  ops/s
//BenchmarkConfig.bench_sumPrimitiveCycleWithIf        thrpt    5  11,757 ± 1,774  ops/s
//BenchmarkConfig.bench_sumPrimitiveFlatMap            thrpt    5   0,736 ± 0,024  ops/s
//BenchmarkConfig.bench_sumPrimitiveMapMulti           thrpt    5  32,177 ± 4,770  ops/s
//BenchmarkConfig.bench_sumPrimitiveStreamWithFilter   thrpt    5  15,891 ± 3,083  ops/s


//Benchmark                                             Mode  Cnt   Score   Error  Units

