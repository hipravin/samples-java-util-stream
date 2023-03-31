package com.hipravin.post;

import java.util.List;

public interface PostIndexRepository {
    List<PostIndex> findByIndexStartingWith(String prefix, int limit);
}
