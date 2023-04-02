package com.hipravin.stream.create;

import java.util.stream.Stream;

public class BuildAccept {
    public static void main(String[] args) {
        Stream.Builder<String> builder = Stream.builder();
        builder.accept("a");
        builder.accept("b");
        builder.accept("c");

        Stream<String> someLetters = builder.build();

        someLetters.forEach(s -> System.out.println(s));
    }
}
