package com.spring.melon;

import com.spring.melon.repository.UrlRepository;
import com.spring.melon.service.UrlGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final UrlRepository urlRepository;

    @Autowired
    public SpringConfig(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }
}
