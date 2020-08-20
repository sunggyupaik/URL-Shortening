package com.spring.melon.repository;
import com.spring.melon.domain.Urls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepositoryImpl extends JpaRepository<Urls, Long>, UrlRepository {
//    @Query("select Url from Url where shortenedUrl = ?1")
//    Url findByShortenedUrl(@Param("shortenedUrl") String shortenedUrl);

    @Override
    Optional<Urls> findByShortenedUrl(String ShortenedUrl);

    @Override
    Optional<Urls> findByOriginalUrl(String OriginalUrl);
}
