package com.spring.melon.repository;

import com.spring.melon.domain.Urls;
import com.spring.melon.dto.UrlCreateRequestDto;

import java.util.Optional;


public interface UrlRepository {
    Urls save(Urls url);
    Optional<Urls> findByShortenedUrl(String ShortenedUrl);
    Optional<Urls> findByOriginalUrl(String OriginalUrl);
}
