package com.hipravin.post.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostIndexDto {
    private String index;
    private String description;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PostIndexDto(@JsonProperty("index") String index,
                        @JsonProperty("description") String description) {
        this.index = index;
        this.description = description;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
