package com.hipravin.post.api;

import com.hipravin.post.api.model.DtoMappers;
import com.hipravin.post.api.model.PostIndexDto;
import com.hipravin.post.persist.PostIndexJpaRepository;
import com.hipravin.post.persist.entity.PostIndexEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;

@Service
@Qualifier("postServiceDb")
public class PostServiceDbImpl implements PostService {
    private final PostIndexJpaRepository postIndexJpaRepository;

    public PostServiceDbImpl(PostIndexJpaRepository postIndexJpaRepository) {
        this.postIndexJpaRepository = postIndexJpaRepository;
    }

    @Override
    @Transactional
    public List<PostIndexDto> searchIndexStartingWith(String indexPrefix, int limit) {
        try (Stream<PostIndexEntity> indexEntityStream = postIndexJpaRepository
                .findByIndexStartingWith(indexPrefix, PageRequest.of(0, limit))) {
            return indexEntityStream.map(DtoMappers::fromEntity)
                    .toList();
        }
    }

//    @Override
    public List<PostIndexDto> searchIndexStartingWithListImpl(String indexPrefix, int limit) {
        return postIndexJpaRepository.findByIndexStartingWithListImpl(
                        indexPrefix, PageRequest.of(0, limit))
                .stream()
                .map(DtoMappers::fromEntity)
                .toList();
    }


}
