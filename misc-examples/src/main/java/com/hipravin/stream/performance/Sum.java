package com.hipravin.stream.performance;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class Sum {
    public static long sumPrimitiveStream(long sumToInclusive) {
        return LongStream.rangeClosed(0, sumToInclusive).sum();
    }

    public static long sumPrimitiveStreamParallel(long sumToInclusive) {
        return LongStream.rangeClosed(0, sumToInclusive).parallel().sum();
    }

    public static long sumBoxedStream(long sumToInclusive) {
        return LongStream.rangeClosed(0, sumToInclusive).boxed()
                .reduce(0L, Long::sum);
    }

    public static long sumBoxedStreamParallel(long sumToInclusive) {
        return LongStream.rangeClosed(0, sumToInclusive).boxed()
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static long sumPrimitiveCycle(long sumToInclusive) {
        long sum = 0;
        for (long l = 0; l <= sumToInclusive; l++) {
            sum += l;
        }
        return sum;
    }

    public static long sumBoxedCycle(long sumToInclusive) {
        Long sum = 0L;
        for (Long l = 0L; l <= sumToInclusive; l++) {
            sum += l;
        }
        return sum;
    }

    public static long sumPrimitiveFlatMap(long sumToInclusive) {
        return LongStream.rangeClosed(0, sumToInclusive)
                .flatMap(l -> LongStream.of(l)) //useless operation to measure flatmap overhead
                .sum();
    }

    public static long sumPrimitiveFlatMapParallel(long sumToInclusive) {
        return LongStream.rangeClosed(0, sumToInclusive)
                .flatMap(l -> LongStream.of(l)) //useless operation to measure flatmap overhead
                .parallel()
                .sum();
    }

    public static long sumPrimitiveMapMulti(long sumToInclusive) {
        return LongStream.rangeClosed(0, sumToInclusive)
                .mapMulti((l, c) -> c.accept(l)) //useless operation to measure mapmulti overhead
                .sum();
    }

    public static long sumAtomicLong(long sumToInclusive) {
        AtomicLong sum = new AtomicLong(0L);
        for (long l = 0L; l <= sumToInclusive; l++) {
            sum.addAndGet(l);
        }
        return sum.get();
    }

    public static long sumBigInteger(long sumToInclusive) {
        BigInteger sum = BigInteger.ZERO;
        for (long l = 0L; l <= sumToInclusive; l++) {
            sum = sum.add(BigInteger.valueOf(l));
        }
        return sum.longValueExact();
    }

    public static long sumPrimitiveCycleWithIf(long sumToInclusive) {
        long sum = 0;
        for (long l = 0; l <= sumToInclusive; l++) {
            if((l & 1) == 1) {
                sum += l;
            }
        }
        return sum;
    }

    public static long sumPrimitiveStreamWithFilter(long sumToInclusive) {
        return LongStream.rangeClosed(0, sumToInclusive)
                .filter(l -> (l & 1) == 1)
                .sum();
    }
}
