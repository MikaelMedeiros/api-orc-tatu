package com.codejokers.orctatu.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CacheConfig implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

    @Override
    public void customize(final ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(List.of("instrospect-user-info", "authentication-url"));
    }
}