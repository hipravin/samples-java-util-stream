package com.hipravin.post.persist;

import com.hipravin.post.persist.entity.PostIndexEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.stream.Stream;

@Repository
public class PostIndexDaoImpl implements PostIndexDao {
    private final EntityManager em;
    private final PostIndexJpaRepository postIndexJpaRepository;
    private final PlatformTransactionManager platformTransactionManager;

    public PostIndexDaoImpl(EntityManager em,
                            PostIndexJpaRepository postIndexJpaRepository,
                            PlatformTransactionManager platformTransactionManager) {
        this.em = em;
        this.postIndexJpaRepository = postIndexJpaRepository;
        this.platformTransactionManager = platformTransactionManager;
    }


    @Override
    public void saveAll(Stream<PostIndexEntity> postIndexEntityStream) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);

        transactionTemplate.executeWithoutResult((ts) -> {
            postIndexEntityStream.forEach(pie -> em.persist(pie));
        });
    }

    @Transactional
    public void deleteAll() {
        postIndexJpaRepository.deleteAll();
    }

    public void saveAllJpaRepo(Stream<PostIndexEntity> postIndexEntityStream) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);

        transactionTemplate.executeWithoutResult((ts) -> {
            postIndexJpaRepository.saveAll(() -> postIndexEntityStream.iterator());
        });
    }
}
