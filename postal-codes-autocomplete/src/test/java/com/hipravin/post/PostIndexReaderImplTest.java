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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PostIndexReaderImplTest {
    private static final int SAMPLE_INDICES_COUNT = 58444;

    static PostIndexReader postIndexReader;
    static PostIndexRepository postIndexRepository;


    @BeforeAll
    static void beforeAll() throws URISyntaxException {
        Path sampleFile = Path.of(ClassLoader.getSystemResource("data/PIndx05.dbf").toURI());
        postIndexReader = new PostIndexReaderImpl(sampleFile);
        try(Stream<PostIndex> postIndices = postIndexReader.readAll()) {
            postIndexRepository = new PostIndexInMemoryRepository(postIndices.toList());
        }
    }

    @Test
    void readAllSample() {
        List<PostIndex> postIndices = postIndexReader.readAllList();
        assertEquals(SAMPLE_INDICES_COUNT, postIndices.size());
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
        assertEquals(SAMPLE_INDICES_COUNT, postIndices.size());
    }

    @Test
    void testReadAllProperClose() {
        try(Stream<PostIndex> postIndexStream = postIndexReader.readAll()) {
            long postIndicesCount = postIndexStream.count();
            assertEquals(SAMPLE_INDICES_COUNT, postIndicesCount);
        }
    }

    @Test
    void testReadAllParallelStreamSample() {
        try(Stream<PostIndex> postIndexStream = postIndexReader.readAll()) {
            List<PostIndex> postIndices = postIndexStream.parallel().toList();
            assertEquals(SAMPLE_INDICES_COUNT, postIndices.size());
        }
    }

    @Test
    void testReadAllStreamSpliteratorSample() {
        PostIndexReaderImpl postIndexReaderImpl = (PostIndexReaderImpl) postIndexReader;
        try(Stream<PostIndex> postIndexStream = postIndexReaderImpl.readAllStreamSpliterator()) {
            List<PostIndex> postIndices = postIndexStream.toList();
            assertEquals(SAMPLE_INDICES_COUNT, postIndices.size());
        }
    }

    @Test
    void testReadAllStreamSpliteratorSampleCount() {
        PostIndexReaderImpl postIndexReaderImpl = (PostIndexReaderImpl) postIndexReader;
        try(Stream<PostIndex> postIndexStream = postIndexReaderImpl.readAllStreamSpliterator()) {
            assertEquals(SAMPLE_INDICES_COUNT, postIndexStream.count());
        }
    }

    @Test
    void testReadAllParallelStreamSpliteratorSample() {
        PostIndexReaderImpl postIndexReaderImpl = (PostIndexReaderImpl) postIndexReader;
        try(Stream<PostIndex> postIndexStream = postIndexReaderImpl.readAllStreamSpliterator()) {
            List<PostIndex> postIndices = postIndexStream.parallel().toList();
            assertEquals(SAMPLE_INDICES_COUNT, postIndices.size());
        }
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
        assertEquals(SAMPLE_INDICES_COUNT, indices.size());
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