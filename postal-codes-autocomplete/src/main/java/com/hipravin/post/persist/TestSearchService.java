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
                .peek(e -> {throw new RuntimeException(" on purpose");})
                .toList();
    }

    @Transactional
    public List<PostIndexEntity> searchTerminalNoCloseExceptionRepeated(String indexPrefix) {
        for (int i = 0; i < 10_000; i++) {
            try {
                postIndexJpaRepository.findByIndexStartingWith(indexPrefix, PageRequest.of(0, 100))
                        .peek(e -> {
                            throw new RuntimeException(" on purpose");
                        })
                        .toList();
            } catch (RuntimeException e) {
                //ignore
            }
            if(i % 100 == 0) {//periodically checking that normal queries still work fine
                System.out.println("iteration #" + i);
                postIndexJpaRepository.findByIndexStartingWith(indexPrefix, PageRequest.of(0, 1))
                        .forEach(pie -> System.out.println(pie.getName()));
            }
        }

        postIndexJpaRepository.findByIndexStartingWith(indexPrefix, PageRequest.of(0, 10))
                .forEach(pie -> System.out.println(pie.getName()));

        return List.of();
    }

    @Transactional
    public List<PostIndexEntity> searchExceptionRepeatedProperClose(String indexPrefix) {

        for (int i = 0; i < 100_000; i++) {
            try(Stream<PostIndexEntity> postIndexes = postIndexJpaRepository.findByIndexStartingWith(indexPrefix, PageRequest.of(0, 100))) {
                        postIndexes.peek(e -> {
                            throw new RuntimeException(" on purpose");
                        })
                        .toList();
            } catch (RuntimeException e) {
                //ignore
            }
            if(i % 100 == 0) {
                System.out.println("iteration #" + i);
                postIndexJpaRepository.findByIndexStartingWith(indexPrefix, PageRequest.of(0, 1))
                        .forEach(pie -> System.out.println(pie.getName()));
            }
        }

        postIndexJpaRepository.findByIndexStartingWith(indexPrefix, PageRequest.of(0, 10))
                .forEach(pie -> System.out.println(pie.getName()));

        return List.of();
    }
}
