package com.spring.melon.service;

import com.spring.melon.domain.Urls;
import com.spring.melon.exception.NotFoundException;
import com.spring.melon.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class UrlGetService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlGetService(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }

    @Cacheable(value="originalurl", key="#shortenedUrl")
    public String findByShortenedUrl(String shortenedUrl){
        Urls url = urlRepository.findByShortenedUrl(shortenedUrl)
                .orElseThrow(NotFoundException::new);
        return url.getOriginalUrl();
    }

    @Cacheable(value="shortenedurl", key="#originalUrl")
    public String findByOriginalUrl(String originalUrl){
        Urls url = urlRepository.findByOriginalUrl(originalUrl)
                .orElseThrow(NotFoundException::new);
        return url.getShortenedUrl();
    }
}
