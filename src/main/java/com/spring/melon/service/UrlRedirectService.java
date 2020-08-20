package com.spring.melon.service;

import com.spring.melon.domain.Urls;
import com.spring.melon.exception.NotFoundException;
import com.spring.melon.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UrlRedirectService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlRedirectService(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }

    public String makeUrlForRedirect(String originalUrl){
        return originalUrl;
    }

}
