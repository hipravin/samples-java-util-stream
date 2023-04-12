package com.hipravin.post.api;

import com.hipravin.post.api.model.PostIndexDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post/")
public class PostController {
    private final PostService postServiceInMemory;
    private final PostService postServiceDb;

    public PostController(@Qualifier("postServiceInMemory") PostService postServiceInMemory,
                          @Qualifier("postServiceDb") PostService postServiceDb
                          ) {
        this.postServiceInMemory = postServiceInMemory;
        this.postServiceDb = postServiceDb;
    }

    @GetMapping(value = "/inmem/index/search")
    public ResponseEntity<List<PostIndexDto>> searchInMemory(
            @RequestParam("q") @NotBlank String query,
            @RequestParam(value = "lmt", required = false, defaultValue = "10") @Min(1) int limit) {

        List<PostIndexDto> result = postServiceInMemory.searchIndexStartingWith(query, limit);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/db/index/search")
    public ResponseEntity<List<PostIndexDto>> searchDb(
            @RequestParam("q") @NotBlank String query,
            @RequestParam(value = "lmt", required = false, defaultValue = "10") @Min(1) int limit) {

        List<PostIndexDto> result = postServiceDb.searchIndexStartingWith(query, limit);
        return ResponseEntity.ok(result);
    }
}
