package com.hipravin.stream.create;

import java.util.Arrays;
import java.util.stream.Stream;

public class Fixed {
    public static void main(String[] args) {
        //syntactic sugar over Arrays.stream
        Stream<String> someLetters = Stream.of("a", "b", "c");
        Stream<String> emptyStream1 = Stream.of();
        Stream<String> emptyStream2 = Stream.empty();

        String[] words = "Betty Botter bought some butter".split("\\s+");
        Stream<String> streamFromArray = Arrays.stream(words);


    }
}
