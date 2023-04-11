package com.hipravin.post.persist;

import com.hipravin.post.PostIndex;
import com.hipravin.post.persist.entity.EntityMappers;
import com.hipravin.post.persist.entity.PostIndexEntity;
import com.hipravin.post.reader.PostIndexReader;
import com.hipravin.post.reader.PostIndexReaderImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
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

//    @Test
//    void testSaveAll() {
//        processPostIndexStream(postIndices -> {
//            Stream<PostIndexEntity> entitiesStream = postIndices.map(pi -> EntityMappers.from(pi));
//            Iterable<PostIndexEntity> entitiesIterable = () -> entitiesStream.iterator();
//            postIndexJpaRepository.saveAll(entitiesIterable);
//        });
//
//        long totalRecords = postIndexJpaRepository.count();
//        System.out.println("Total records: " + totalRecords);
//    }


    @Transactional
    @RepeatedTest(100)
    void findByPrefixStream() {
        for (int i = 0; i < 100; i++) {
            Stream<PostIndexEntity> result = postIndexJpaRepository.findByIndexStartingWith("10", PageRequest.of(0, 10));
        }

//        result.findFirst().ifPresent(pi -> System.out.println(pi.getIndex() + " / " + pi.getName()));
//        result.close();

    }


    @Transactional
    @RepeatedTest(100)
    void findByPrefixStreamCloseStream() {
        try(Stream<PostIndexEntity> result = postIndexJpaRepository.findByIndexStartingWith("10", PageRequest.of(0, 10));) {
            result.findFirst().ifPresent(pi -> System.out.println(pi.getIndex() + " / " + pi.getName()));
        }
    }

    static void processPostIndexStream(Consumer<Stream<PostIndex>> processor) {
        try (Stream<PostIndex> postIndices = postIndexReader.readAll()) {
            processor.accept(postIndices);
        }
    }
}