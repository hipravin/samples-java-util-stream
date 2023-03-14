package com.hipravin.stream;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamPlaygroundTest {

    @Test
    public void testParallelSequential() {
        Stream<String> names = Stream.of("Name1", "Name2", "Name3", "Name4");
        assertFalse(names.isParallel());
        assertTrue(names.sequential().parallel().isParallel());
        assertFalse(names.parallel().sequential().isParallel());
    }

    @Test
    void testLotOfOps() {
        Stream<String> names = Stream.of("Name1", "Name2", "Name3", "Name4");

        System.out.println(names);

        Stream<Character> letters = names
                .map(s -> s.concat(s))
                .map(s -> s.concat(" is doubled"))
                .limit(3)
                .filter(s -> !s.contains("3"))
                .flatMap(s -> s.chars().mapToObj(i -> (char) i))
                .peek(c -> System.out.println(c));


        System.out.println(letters.toList());

    }

    @Test
    void testParallelStream() {
        List<String> names = List.of("Name1", "Name2", "Name3", "Name4");
        //any difference ?
        names.parallelStream();
        names.stream().parallel();
        //\


    }
}
