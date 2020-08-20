package com.spring.melon.dto;

import com.spring.melon.domain.Urls;
import com.spring.melon.exception.ErrorResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UrlCreateRequestDto {
    private String shortenedUrl;
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    @Builder
    public UrlCreateRequestDto(String shortenedUrl, String originalUrl){
        this.shortenedUrl = shortenedUrl;
        this.originalUrl = originalUrl;
    }

    public Urls toEntity(){
        return Urls.builder()
                .originalUrl(originalUrl)
                .shortenedUrl(shortenedUrl)
                .build();
    }
}
