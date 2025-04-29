package com.presentation.demo_rag.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@Configuration
public class ResourceConfig {

    private final ResourcePatternResolver resourcePatternResolver;

    public ResourceConfig(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    @Bean
    public Resource[] resources() {
        try {
            return resourcePatternResolver.getResources("classpath:/docs/*");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load resources from classpath:/docs/*", e);
        }
    }

}
