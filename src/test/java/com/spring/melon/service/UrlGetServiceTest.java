package com.spring.melon.service;

import com.spring.melon.domain.Urls;
import com.spring.melon.dto.UrlCreateRequestDto;
import com.spring.melon.exception.NotFoundException;
import com.spring.melon.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UrlGetServiceTest {

    @Autowired
    UrlGetService urlGetService;

    @Autowired
    UrlCreateService urlCreateService;

    @Autowired
    UrlRepository urlRepository;

    @Test
    public void Url생성(){
        //given
        String originalUrl = "a";
        UrlCreateRequestDto requestDto = UrlCreateRequestDto.builder()
                .originalUrl(originalUrl)
                .shortenedUrl("")
                .build();

        //when
        String shorted = urlCreateService.createShortenedUrl(requestDto);
        Urls url = urlRepository.findByOriginalUrl(originalUrl)
                .orElseThrow( () -> new IllegalStateException("에러"));
        String shortenedUrl = url.getShortenedUrl();

        //then
        assertThat(shortenedUrl).isEqualTo(shorted);
    }

    @Test
    public void Url찾기(){
        //given
        Urls url = Urls.builder()
                .originalUrl("a")
                .shortenedUrl("b")
                .build();

        //when
        urlRepository.save(url);
        String shortenedUrl = urlGetService.findByOriginalUrl(url.getOriginalUrl());

        //then
        assertThat(shortenedUrl).isEqualTo(url.getShortenedUrl());
    }
}
