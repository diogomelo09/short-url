package com.api.shortener_url.repository;

import com.api.shortener_url.entities.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    UrlEntity findByShortUrl(String shortUrl);

    Optional<UrlEntity> findByShortUrlAndLocalDate(String shortUrl, LocalDate localDate);
}