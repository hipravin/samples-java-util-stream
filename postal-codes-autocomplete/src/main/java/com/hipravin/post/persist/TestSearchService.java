package com.hipravin.post.persist;

import com.hipravin.post.persist.entity.PostIndexEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TestSearchService {
    private final PostIndexJpaRepository postIndexJpaRepository;

    public TestSearchService(PostIndexJpaRepository postIndexJpaRepository) {
        this.postIndexJpaRepository = postIndexJpaRepository;
    }

    @Transactional
    public List<PostIndexEntity> search(String indexPrefix) {
        try(Stream<PostIndexEntity> indices = postIndexJpaRepository.findByIndexStartingWith(indexPrefix, PageRequest.of(0, 10))) {
            return indices.toList();
        }
    }

    @Transactional
    public List<PostIndexEntity> searchNoTerminalNoClose(String indexPrefix) {
        postIndexJpaRepository.findByIndexStartingWith(indexPrefix, PageRequest.of(0, 10));
        //simulate no terminal operation -> no close called even with decorator
        return List.of();
    }

    @Transactional
    public List<PostIndexEntity> searchTerminalNoCloseException(String indexPrefix) {
        //simulate exception on intermediate operation
        return postIndexJpaRepository.findByIndexStartingWith(indexPrefix, PageRequest.of(0, 10))
//                .peek(e -> {throw new RuntimeException(" on purpose");})
                .toList();
    }
}
