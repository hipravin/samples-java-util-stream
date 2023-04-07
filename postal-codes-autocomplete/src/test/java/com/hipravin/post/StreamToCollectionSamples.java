package com.hipravin.post;

import com.hipravin.post.reader.PostIndexReader;
import com.hipravin.post.reader.PostIndexReaderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StreamToCollectionSamples {
    private static final int SAMPLE_INDICES_COUNT = 58444;

    PostIndexReader postIndexReader;
    Stream<PostIndex> indicesStream;

    @BeforeEach
    void setUp() throws URISyntaxException {
        Path sampleFile = Path.of(ClassLoader.getSystemResource("data/PIndx05.dbf").toURI());
        postIndexReader = new PostIndexReaderImpl(sampleFile);
        indicesStream = postIndexReader.readAll();
    }

    @AfterEach
    void tearDown() {
        indicesStream.close();
    }

    @Test
    void sampleListOfAllCollect() {
        List<PostIndex> indices = indicesStream.collect(Collectors.toList());
        //java.util.ArrayList
        //but "There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned"
        System.out.println(indices.getClass());
        //((ArrayList) indices).elementData.length = 71140
        assertEquals(SAMPLE_INDICES_COUNT, indices.size());
    }

    @Test
    void sampleListOfAllToList() {
        List<PostIndex> indices = indicesStream.toList();//since java 16
        //class java.util.ImmutableCollections$ListN
        System.out.println(indices.getClass());
        //((ImmutableCollections.ListN) indices).elements.length = 58444
        assertEquals(SAMPLE_INDICES_COUNT, indices.size());
    }

    @Test
    void sampleListOfAllToArrayList() {
        ArrayList<PostIndex> indices = indicesStream.collect(Collectors.toCollection(ArrayList::new));
        //class java.util.ArrayList
        System.out.println(indices.getClass());
        //((ArrayList) indices).elementData.length = 71140
        assertEquals(SAMPLE_INDICES_COUNT, indices.size());
    }

    @Test
    void sampleListOfMoscow() {
        List<PostIndex> indices = indicesStream
                .filter(pi -> pi != null)
                .filter(pi -> "МОСКВА".equals(pi.region()))
                .collect(Collectors.toList());
        System.out.println(indices.getClass());

        assertEquals(5442, indices.size());
    }

    @Test
    void sampleToSet() {
        Set<PostIndex> indicesSet = indicesStream.collect(Collectors.toSet());
        //java.util.HashSet
        System.out.println(indicesSet.getClass());
        assertEquals(SAMPLE_INDICES_COUNT, indicesSet.size());
    }

    @Test
    void sampleToHashSet() {
        Set<PostIndex> indicesSet = indicesStream.collect(
                Collectors.toCollection(HashSet::new));
        assertEquals(SAMPLE_INDICES_COUNT, indicesSet.size());
    }

    @Test
    void sampleToTreeSetNoComparatorClassCastException() {
        assertThrows(ClassCastException.class, () -> {
            indicesStream.collect(
                    Collectors.toCollection(() -> new TreeSet<>()));
        });
    }

    @Test
    void sampleToTreeSetCorrect() {
        SortedSet<PostIndex> indicesSet = indicesStream.collect(
                Collectors.toCollection(() -> new TreeSet<>(PostIndex.BY_INDEX_THEN_OTHER_COMPARATOR)));
        //java.util.TreeSet
        System.out.println(indicesSet.getClass());
        assertEquals(SAMPLE_INDICES_COUNT, indicesSet.size());
    }



}
