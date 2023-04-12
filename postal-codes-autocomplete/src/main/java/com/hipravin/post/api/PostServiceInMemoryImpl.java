package com.hipravin.post.api;

import com.hipravin.post.PostIndexRepository;
import com.hipravin.post.api.model.DtoMappers;
import com.hipravin.post.api.model.PostIndexDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("postServiceInMemory")
public class PostServiceInMemoryImpl implements PostService {
    private final PostIndexRepository postIndexRepository;

    public PostServiceInMemoryImpl(
            @Qualifier("postIndexRepositoryInMemory") PostIndexRepository postIndexRepository) {
        this.postIndexRepository = postIndexRepository;
    }

    @Override
    public List<PostIndexDto> searchIndexStartingWith(String indexPrefix, int limit) {
        return postIndexRepository.findByIndexStartingWith(indexPrefix, limit)
                .stream()
                .map(DtoMappers::fromDomain)
                .toList();
    }
}
