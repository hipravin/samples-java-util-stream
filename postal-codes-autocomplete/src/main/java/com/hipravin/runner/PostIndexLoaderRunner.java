package com.hipravin.runner;

import com.hipravin.post.PostIndex;
import com.hipravin.post.persist.PostIndexDao;
import com.hipravin.post.persist.PostIndexDaoImpl;
import com.hipravin.post.persist.entity.EntityMappers;
import com.hipravin.post.persist.entity.PostIndexEntity;
import com.hipravin.post.reader.PostIndexReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@ConditionalOnProperty(
        value = "application.runners.loadindices.enabled",
        havingValue = "true"
)
@Order(Runners.ORDER_LOAD_POST_INDICES)
public class PostIndexLoaderRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(PostIndexLoaderRunner.class);

    private final PostIndexReader postIndexReader;
    private final PostIndexDao postIndexDao;

    public PostIndexLoaderRunner(PostIndexReader postIndexReader,
                                 PostIndexDao postIndexDao) {
        this.postIndexReader = postIndexReader;
        this.postIndexDao = postIndexDao;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Loading post indices to database - started...");
        try(Stream<PostIndex> postIndexStream = postIndexReader.readAll()) {
            Stream<PostIndexEntity> entityStream = postIndexStream.map(pi -> EntityMappers.from(pi));
            postIndexDao.saveAll(entityStream, 0);
            //https://stackoverflow.com/questions/45635827/how-do-i-stop-spring-data-jpa-from-doing-a-select-before-a-save
//            ((PostIndexDaoImpl)postIndexDao).saveAllJpaRepo(entityStream, 0); //much slower because of isNew
        } catch(RuntimeException e) {
            log.error("Failed to load post indices: {}", e.getMessage(), e);
            throw e;
        }

        log.info("Loading post indices to database - finished.");
    }
}
