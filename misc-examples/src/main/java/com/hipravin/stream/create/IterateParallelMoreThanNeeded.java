package com.hipravin.stream.create;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class IterateParallelMoreThanNeeded {

    public static void main(String[] args) {
        UnaryOperator<Integer> incrementAndPrint = (i) -> {
            System.out.println("increment " + i);
            return i + 1;
        };

        Stream.iterate(0,  incrementAndPrint)
                .limit(10)
                .parallel()
                .forEach(i -> System.out.print(i + ", "));
    }
}