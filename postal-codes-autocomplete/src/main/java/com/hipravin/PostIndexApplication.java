package com.hipravin;


import com.hipravin.config.ApplicationProperties;
import com.hipravin.post.PostIndex;
import com.hipravin.post.PostIndexInMemoryRepository;
import com.hipravin.post.PostIndexRepository;
import com.hipravin.post.reader.PostIndexReader;
import com.hipravin.post.reader.PostIndexReaderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootApplication
@EnableConfigurationProperties({
        ApplicationProperties.class})
public class PostIndexApplication {
    @Autowired
    ApplicationProperties applicationProperties;

    @Bean
    PostIndexReader postIndexReader() {
        return new PostIndexReaderImpl(Paths.get(applicationProperties.getPostIndexFilePath()));
    }

    @Bean
    @Qualifier("postIndexRepositoryInMemory")
    PostIndexRepository postIndexRepositoryInMemory() {
        try(Stream<PostIndex> postIndices = postIndexReader().readAll()) {
            return PostIndexInMemoryRepository.fromStream(postIndices);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(PostIndexApplication.class, args);
    }
}
