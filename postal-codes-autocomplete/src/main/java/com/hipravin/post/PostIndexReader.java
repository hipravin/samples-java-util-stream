package com.hipravin.post;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface PostIndexReader {
    Stream<PostIndex> readAll();
    List<PostIndex> readAllList();
    void readAll(Consumer<? super PostIndex> handler);
}
