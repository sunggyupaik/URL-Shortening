package com.spring.melon.controller;


import com.spring.melon.domain.SuccessResponse;
import com.spring.melon.dto.UrlCreateRequestDto;
import com.spring.melon.service.UrlCreateService;
import com.spring.melon.service.UrlGetService;
import com.spring.melon.service.UrlRedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.net.URI;


@RestController
public class UrlController {

    @Autowired
    private UrlGetService urlGetService;

    @Autowired
    private UrlCreateService urlCreateService;

    @Autowired
    private UrlRedirectService urlRedirectService;

    @GetMapping("/")
    public String home(){
        return "index";
    }


    @GetMapping("/urls/shortenedurls/{originalurl}")
    public ResponseEntity<?> findByOriginalUrl(@PathVariable String originalurl){
        String shortenedUrl = urlGetService.findByOriginalUrl(originalurl);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(200)
                .message("OK")
                .url(shortenedUrl)
                .build();

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/urls/originalurls/{shortenedurl}")
    public ResponseEntity<?> findByShortenedUrl(@PathVariable String shortenedurl){
        String originalUrl = urlGetService.findByShortenedUrl(shortenedurl);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(200)
                .message("OK")
                .url(originalUrl)
                .build();

        return  new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/urls")
    public ResponseEntity<?> createShortenedUrl (@RequestBody UrlCreateRequestDto requestDto){
        String shortenedUrl = urlCreateService.createShortenedUrl(requestDto);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(201)
                .message("CREATED")
                .url(shortenedUrl)
                .build();

        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{shortenedurl}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortenedurl){
        String originalUrl = urlGetService.findByShortenedUrl(shortenedurl);

        String redirectUrl = urlRedirectService.makeUrlForRedirect(originalUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

}
