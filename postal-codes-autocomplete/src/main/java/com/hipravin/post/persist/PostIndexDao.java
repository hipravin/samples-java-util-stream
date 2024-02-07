package com.hipravin.post.persist;

import com.hipravin.post.persist.entity.PostIndexEntity;

import java.util.stream.Stream;

public interface PostIndexDao {
    void saveAll(Stream<PostIndexEntity> postIndexEntityStream);
    void deleteAll();
}
