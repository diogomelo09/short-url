package com.api.shortener_url.service;

import com.api.shortener_url.controller.dto.UrlRequest;
import com.api.shortener_url.controller.dto.UrlResponse;
import com.api.shortener_url.entities.UrlEntity;
import com.api.shortener_url.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UrlService {

    public UrlEntity findByShortUrl(String shortUrl) {
        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl);

        return urlEntity;
    }

    public Optional<UrlEntity> findByShortUrlAndDate(String shortUrl, LocalDate localDate) {
        return urlRepository.findByShortUrlAndLocalDate(shortUrl, localDate);
    }


    public UrlResponse addNewUrl(UrlRequest request, HttpServletRequest servletRequest) {

        String fullUrl = request.url();
        String shortUrl = generateUniqueShortUrl(fullUrl);
        LocalDate localDate = LocalDate.now();

        String searchUrl = "0";

        UrlEntity urlEntity = new UrlEntity(fullUrl, shortUrl, localDate, searchUrl);
        urlRepository.save(urlEntity);

        return new UrlResponse(shortUrl);
    }

    public void incrementAccessCount(String shortUrl) {
        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl);

        if (urlEntity != null) {

            int currentCount = Integer.parseInt(urlEntity.getSearchUrl());
            int newCount = currentCount + 1;


            urlEntity.setSearchUrl(String.valueOf(newCount));
            urlRepository.save(urlEntity);
        }
    }


    private String generateUniqueShortUrl(String fullUrl) {
        String shortUrl;
        do {
            shortUrl = RandomStringUtils.randomAlphanumeric(6, 12);
        } while (urlRepository.findByShortUrl(shortUrl) != null);
        return shortUrl;
    }

    private final UrlRepository urlRepository;
}
