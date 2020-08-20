package com.spring.melon.service;

import com.spring.melon.dto.UrlCreateRequestDto;
import com.spring.melon.exception.ConflictException;
import com.spring.melon.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UrlCreateService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlCreateService(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }

    public String createShortenedUrl(UrlCreateRequestDto requestDto){
        //이미 존재하는 URL에 대한 중복 검사
        urlRepository.findByOriginalUrl(requestDto.getOriginalUrl())
                .ifPresent(u -> {
                    throw new ConflictException();});
        //urlRepository.save(requestDto.toEntity()).getShortened();

        //너무 짧아서 단축 URL을 할 수 없는 상황에 대한 검사

        String shortenedUrl = base62Convert(requestDto.getOriginalUrl());
        UrlCreateRequestDto newRequestDto = UrlCreateRequestDto.builder()
                .originalUrl(requestDto.getOriginalUrl())
                .shortenedUrl(shortenedUrl)
                .build();

        return urlRepository.save(newRequestDto.toEntity()).getShortenedUrl();
    }

    public String base62Convert(String originalUrl){
        final int RADIX = 62;
        final String CODEC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer sb = new StringBuffer();
        long val = 0;

        for(int i=0;i<originalUrl.length();i++) {
            val = 31 * val + originalUrl.charAt(i);
        }

        while(val > 0) {
            sb.append(CODEC.charAt((int) (val % RADIX)));
            val /= RADIX;
        }
        return sb.toString();
    }
}
