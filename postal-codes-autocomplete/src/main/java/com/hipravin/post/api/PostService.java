package com.hipravin.post.api;

import com.hipravin.post.api.model.PostIndexDto;

import java.util.List;

public interface PostService {
    List<PostIndexDto> searchIndexStartingWith(String indexPrefix, int limit);
}
