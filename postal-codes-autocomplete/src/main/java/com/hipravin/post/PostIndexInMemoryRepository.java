package com.hipravin.post;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Immutable, contents can't be changed after initialization.
 */
public class PostIndexInMemoryRepository implements PostIndexRepository {
    private final SortedMap<String, PostIndex> byIndex;

    private PostIndexInMemoryRepository(SortedMap<String, PostIndex> indicesByIndex) {
        this.byIndex = indicesByIndex;
    }

    public static PostIndexInMemoryRepository fromStream(Stream<PostIndex> indicesStream) {
        BinaryOperator<PostIndex> onDuplicateThrow = (pi1, pi2) -> {
            throw new IllegalStateException("Duplicated post index: " + pi1 + ", " + pi2);
        };

        SortedMap<String, PostIndex> byIndex = indicesStream.collect(
                Collectors.toMap(PostIndex::index, pi -> pi, onDuplicateThrow, TreeMap::new));

        return new PostIndexInMemoryRepository(byIndex);
    }

    @Override
    public List<PostIndex> findByIndexStartingWith(String prefix, int limit) {
        return findByIndexStartingWithStreamImpl(prefix, limit);
    }

    public List<PostIndex> findByIndexStartingWithStreamImpl(String prefix, int limit) {
        return byIndex.tailMap(prefix).values().stream()
                .takeWhile(pi -> pi.index().startsWith(prefix))
                .limit(limit)
                .toList();
    }

    public List<PostIndex> findByIndexStartingWithImperativeImpl(String prefix, int limit) {
        List<PostIndex> result = new ArrayList<>();
        Collection<PostIndex> tail = byIndex.tailMap(prefix).values();

        for (PostIndex postIndex : tail) {
            if (postIndex.index().startsWith(prefix) && result.size() < limit) {
                result.add(postIndex);
            } else {
                break;
            }
        }

        return result;
    }
}
