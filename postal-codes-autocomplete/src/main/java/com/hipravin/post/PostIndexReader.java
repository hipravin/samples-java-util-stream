package com.hipravin.post;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface PostIndexReader {

    /**
     * @apiNote
     * This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open file is closed promptly
     * after the stream's operations have completed.
     */
    Stream<PostIndex> readAll();
    List<PostIndex> readAllList();
    void readAll(Predicate<? super PostIndex> acceptPostIndex,
                 Consumer<? super PostIndex> handler);
}
