package com.hipravin.stream.create;

import java.util.stream.Stream;

public class IterateFibonacci {
    public record Fibbohacci(
            long previous,
            long current) {
        public static Fibbohacci FIRST = new Fibbohacci(0, 1);

        Fibbohacci next() {
            return new Fibbohacci(current, Math.addExact(previous, current));//stream will end with exception but not with incorrect result
        }
    }

    public static void main(String[] args) {
        Stream<Fibbohacci> fibonacciNUmbers = Stream.iterate(Fibbohacci.FIRST, Fibbohacci::next);
        fibonacciNUmbers.forEach(f -> System.out.println(f.current()));
    }
}
