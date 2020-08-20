package com.spring.melon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="Urls")
public class Urls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="originalurl")
    String originalUrl;

    @Column(name="shortenedurl")
    String shortenedUrl;

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    @Builder
    public Urls(String originalUrl, String shortenedUrl){
        this.originalUrl=originalUrl;
        this.shortenedUrl=shortenedUrl;
    }
}
