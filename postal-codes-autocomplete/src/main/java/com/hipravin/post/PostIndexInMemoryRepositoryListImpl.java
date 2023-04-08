package com.hipravin.post;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.hipravin.post.PostIndex.BY_INDEX_THEN_OTHER_COMPARATOR;

/**
 * Immutable, contents can't be changed after initialization.
 */
public class PostIndexInMemoryRepositoryListImpl implements PostIndexRepository {
    /**
     * Pre-sorted list of post indices.
     */
    private final List<PostIndex> indices;

    private PostIndexInMemoryRepositoryListImpl(List<PostIndex> indices) {
        this.indices = indices;
    }

    public static PostIndexInMemoryRepositoryListImpl fromStream(Stream<PostIndex> indicesStream) {
        List<PostIndex> indicesList = indicesStream
                .sorted(BY_INDEX_THEN_OTHER_COMPARATOR)
                .collect(Collectors.toCollection(ArrayList::new));
        return new PostIndexInMemoryRepositoryListImpl(indicesList);
    }

    @Override
    public List<PostIndex> findByIndexStartingWith(String prefix, int limit) {
        PostIndex stubPrefixPostIndex = new PostIndex(
                prefix, "", "", "", "", "", "");
        int binarySearchResult = Collections.binarySearch(
                indices, stubPrefixPostIndex, BY_INDEX_THEN_OTHER_COMPARATOR);
        int fromPosition = binarySearchResult >= 0
                ? binarySearchResult
                : -(binarySearchResult + 1);

        int toPositionExclusive = Math.min(indices.size(), fromPosition + limit);

        List<PostIndex> result = new ArrayList<>();
        for (int i = fromPosition;
             (i < toPositionExclusive) && indices.get(i).index().startsWith(prefix); i++) {
            result.add(indices.get(i));
        }

        return result;
    }
}
