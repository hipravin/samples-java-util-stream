package com.hipravin.stream.create;

import java.util.stream.Stream;

public class IterateLong {

    public static void main(String[] args) {
        Stream<Long> powersOf2 = Stream.iterate(1L, l -> l > 0, l -> l << 1);
        powersOf2.forEach(System.out::println);
    }
}
