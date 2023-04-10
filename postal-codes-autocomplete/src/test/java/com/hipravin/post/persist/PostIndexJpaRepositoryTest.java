package com.hipravin.post.persist;

import com.hipravin.post.PostIndex;
import com.hipravin.post.persist.entity.EntityMappers;
import com.hipravin.post.persist.entity.PostIndexEntity;
import com.hipravin.post.reader.PostIndexReader;
import com.hipravin.post.reader.PostIndexReaderImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
class PostIndexJpaRepositoryTest {
    static PostIndexReader postIndexReader;

    @Autowired
    PostIndexJpaRepository postIndexJpaRepository;

    @BeforeAll
    static void beforeAll() throws URISyntaxException {
        Path sampleFile = Path.of(ClassLoader.getSystemResource("data/PIndx05.dbf").toURI());
        postIndexReader = new PostIndexReaderImpl(sampleFile);
    }

    @Test
    void testSaveAll() {
        processPostIndexStream(postIndices -> {
            Stream<PostIndexEntity> entitiesStream = postIndices.map(pi -> EntityMappers.from(pi));
            Iterable<PostIndexEntity> entitiesIterable = () -> entitiesStream.iterator();
            postIndexJpaRepository.saveAll(entitiesIterable);
        });

        long totalRecords = postIndexJpaRepository.count();
        System.out.println("Total records: " + totalRecords);
    }

    static void processPostIndexStream(Consumer<Stream<PostIndex>> processor) {
        try(Stream<PostIndex> postIndices = postIndexReader.readAll()) {
            processor.accept(postIndices);
        }
    }
}