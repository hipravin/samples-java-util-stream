package com.hipravin.post;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostIndexReaderImplTest {

    static PostIndexReader postIndexReader;
    static PostIndexRepository postIndexRepository;

    @BeforeAll
    static void beforeAll() throws URISyntaxException {
        Path sampleFile = Path.of(ClassLoader.getSystemResource("data/PIndx05.dbf").toURI());
        postIndexReader = new PostIndexReaderImpl(sampleFile);
        postIndexRepository = new PostIndexInMemoryRepository(postIndexReader.readAllList());
    }

    @Test
    void readAllSample() {
        List<PostIndex> postIndices = postIndexReader.readAllList();
        assertEquals(58444, postIndices.size());
    }

    @Test
    void testReadAllStreamSample() {
        List<PostIndex> postIndices = postIndexReader.readAll().toList();
        assertEquals(58444, postIndices.size());
    }

    @Test
    void testNaiveCloseException() {
        PostIndexReaderImpl postIndexreaderImpl = (PostIndexReaderImpl) postIndexReader;

        assertThrows(IllegalArgumentException.class, () -> {
            postIndexreaderImpl.readAllStreamNaive().count();
        });
    }

    @Test
    void testRepositoryFindNothing() {
        List<PostIndex> indices = postIndexRepository.findByIndexStartingWith("xxxxxx", 10);
        assertEquals(0, indices.size());
    }

    @Test
    void testRepositoryFind10() {
        String prefix = "215";
        List<PostIndex> indices = postIndexRepository.findByIndexStartingWith("215", 10);
        indices.forEach(i -> {
            assertTrue(i.index().startsWith(prefix));
        });
    }
}