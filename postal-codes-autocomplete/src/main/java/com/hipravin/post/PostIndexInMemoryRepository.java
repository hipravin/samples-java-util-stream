package com.hipravin.post;

import java.util.*;

/**
 * Immutable, contents can't be changed after initialization.
 */
public class PostIndexInMemoryRepository implements PostIndexRepository {
    private final SortedMap<String, PostIndex> byIndex = new TreeMap<>();

    public PostIndexInMemoryRepository(List<PostIndex> postIndexList) {
        postIndexList.forEach(pi -> {
            byIndex.merge(pi.index(), pi, (pi1, pi2) -> {
                throw new IllegalStateException("Duplicated post index: " + pi1 + ", " + pi2);
            });
        });
    }


    @Override
    public List<PostIndex> findByIndexStartingWith(String prefix, int limit) {
        return findByIndexStartingWithStreamImpl(prefix, limit);
//        return findByIndexStartingWithImperativeImpl(prefix, limit);
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
