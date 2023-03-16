package com.hipravin.stream.zip;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Showcase {
    public static void main(String[] args) {
        Stream<Integer> countingStream = IntStream.iterate(0, i -> i + 1).boxed();
        Stream<String> someLetters = Stream.of("a", "b", "c", "d", "e");

        Stream<Tuple2<Integer, String>> numberedNames = zip(countingStream, someLetters);

        numberedNames.forEach(tuple2 -> {
            System.out.printf("%d: %s%n", tuple2.first(), tuple2.second());
        });
    }

    public static <T1, T2> Stream<Tuple2<T1,T2>> zip(Stream<T1> stream1, Stream<T2> stream2) {
        Iterator<T1> i1 = stream1.iterator();
        Iterator<T2> i2 = stream2.iterator();

        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(new BiIterator<>(i1, i2), Spliterator.ORDERED),
                false)
                .onClose(stream1::close)  //I forgot about this at first, my bad
                .onClose(stream2::close);
    }
}
