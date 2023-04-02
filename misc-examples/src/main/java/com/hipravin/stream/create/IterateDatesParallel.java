package com.hipravin.stream.create;

import java.time.LocalDate;
import java.util.stream.Stream;

public class IterateDatesParallel {

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();

        Stream<LocalDate> oneYearForward = Stream.iterate(today, d -> d.getYear() < today.getYear() + 10, d -> d.plusDays(1))
                .sequential()
                .parallel();

        oneYearForward.forEachOrdered(d -> System.out.println(d + " - " + Thread.currentThread().getName()));
    }
}
