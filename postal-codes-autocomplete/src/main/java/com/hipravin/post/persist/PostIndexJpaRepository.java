package com.hipravin.post.persist;

import com.hipravin.post.persist.entity.PostIndexEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostIndexJpaRepository extends JpaRepository<PostIndexEntity, String> {

}
