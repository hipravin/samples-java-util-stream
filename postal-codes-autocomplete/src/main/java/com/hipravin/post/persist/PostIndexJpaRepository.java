package com.hipravin.post.persist;

import com.hipravin.post.persist.entity.PostIndexEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

public interface PostIndexJpaRepository extends JpaRepository<PostIndexEntity, String> {
    @Query("select pi from PostIndexEntity pi where pi.index like :prefix% order by pi.index")
    Stream<PostIndexEntity> findByIndexStartingWith(@Param("prefix") String prefix, Pageable pageable);

    @Query("select pi from PostIndexEntity pi where pi.index like :prefix% order by pi.index")
    List<PostIndexEntity> findByIndexStartingWithListImpl(@Param("prefix") String prefix, Pageable pageable);
}
