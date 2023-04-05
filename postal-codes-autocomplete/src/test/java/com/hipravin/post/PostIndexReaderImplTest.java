package com.hipravin.post;

import com.hipravin.post.reader.PostIndexReader;
import com.hipravin.post.reader.PostIndexReaderImpl;
import com.linuxense.javadbf.DBFException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

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
    void testReadAllCheckFields() {
        //140570,ТАРБУШЕВО,О,140499,МОСКОВСКАЯ ОБЛАСТЬ,,КОЛОМНА,ОЗЁРЫ,ТАРБУШЕВО
        Optional<PostIndex> postIndexOptional = postIndexReader.readAll()
                .filter(pi -> "140570".equals(pi.index()))
                .findAny();

        assertTrue(postIndexOptional.isPresent());
        PostIndex pi = postIndexOptional.orElseThrow();

        assertEquals("ТАРБУШЕВО", pi.name());
        assertEquals("МОСКОВСКАЯ ОБЛАСТЬ", pi.region());
        assertEquals("КОЛОМНА", pi.area());
        assertEquals("ОЗЁРЫ", pi.city());
        assertEquals("ТАРБУШЕВО", pi.city1());
    }

    @Test
    void testReadAllStreamSample() {
        List<PostIndex> postIndices = postIndexReader.readAll().toList();
        assertEquals(58444, postIndices.size());
    }

    @Test
    void testReadAllParallelStreamSample() {
        List<PostIndex> postIndices = postIndexReader.readAll().parallel()
                .toList();
        assertEquals(58444, postIndices.size());
    }

    @Test
    void testReadAllStreamSpliteratorSample() {
        PostIndexReaderImpl postIndexReaderImpl = (PostIndexReaderImpl) postIndexReader;
        List<PostIndex> postIndices = postIndexReaderImpl.readAllStreamSpliterator().toList();
        assertEquals(58444, postIndices.size());
    }

    @Test
    void testReadAllParallelStreamSpliteratorSample() {
        PostIndexReaderImpl postIndexReaderImpl = (PostIndexReaderImpl) postIndexReader;
        List<PostIndex> postIndices = postIndexReaderImpl.readAllStreamSpliterator().parallel()
                .toList();
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
    void testGenerateLooksFineAtFirstGlance() {
        PostIndexReaderImpl postIndexReaderImpl = (PostIndexReaderImpl) postIndexReader;
        List<PostIndex> indices = postIndexReaderImpl.readAllStreamHackingGenerate().toList();
        assertEquals(58444, indices.size());
    }

    @Test
    void testGenerateIsActuallyBroken() {
        PostIndexReaderImpl postIndexReaderImpl = (PostIndexReaderImpl) postIndexReader;
        assertThrows(DBFException.class, () -> {
            postIndexReaderImpl.readAllStreamHackingGenerate()
                    .parallel().toList();
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