package com.hipravin.stream.create;

import java.util.UUID;
import java.util.stream.Stream;

public class GenerateUuids {

    public static void main(String[] args) {
        Stream<String> uuids = Stream.generate(() -> UUID.randomUUID().toString())
                .limit(10);
        uuids.forEach(System.out::println);
    }
}
