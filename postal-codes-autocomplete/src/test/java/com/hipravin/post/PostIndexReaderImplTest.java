package com.hipravin.post;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

class PostIndexReaderImplTest {

    @Test
    void readAllSample() throws URISyntaxException {
        Path sampleFile = Path.of(ClassLoader.getSystemResource("data/PIndx05.dbf").toURI());
        PostIndexReader postIndexReader = new PostIndexReaderImpl(sampleFile);

        List<PostIndex> postIndices = postIndexReader.readAllList();

        postIndices.stream()
                .limit(1000)
                .forEach(System.out::println);

    }
}