package com.hipravin.stream.create;

import java.util.stream.Stream;

public class BuildAdd {
    public static void main(String[] args) {
        Stream<String> someLetters = Stream.<String>builder()
                .add("a")
                .add("b")
                .add("c")
                .build();

        someLetters.forEach(s -> System.out.println(s));
    }
}
