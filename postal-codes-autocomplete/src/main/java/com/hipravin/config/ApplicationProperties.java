package com.hipravin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private String postIndexFilePath;

    public String getPostIndexFilePath() {
        return postIndexFilePath;
    }

    public void setPostIndexFilePath(String postIndexFilePath) {
        this.postIndexFilePath = postIndexFilePath;
    }
}
