package com.api.shortener_url.service;

import com.api.shortener_url.controller.dto.UrlRequest;
import com.api.shortener_url.controller.dto.UrlResponse;
import com.api.shortener_url.entities.UrlEntity;
import com.api.shortener_url.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewUrl() {
        UrlRequest request = new UrlRequest("http://google.com");
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);

        UrlEntity urlEntity = new UrlEntity("http://facebook.com", "shortUrl", LocalDate.now(), "0");
        when(urlRepository.save(any(UrlEntity.class))).thenReturn(urlEntity);
        when(urlRepository.findByShortUrl(any(String.class))).thenReturn(null); // Simula que o shortUrl não existe

        UrlResponse response = urlService.addNewUrl(request, servletRequest);

        assertEquals("shortUrl", urlEntity.getShortUrl());
        verify(urlRepository).save(any(UrlEntity.class));
    }

    @Test
    public void testIncrementAccessCount() {
        UrlEntity urlEntity = new UrlEntity("http://github.com", "shortUrl", LocalDate.now(), "0");
        when(urlRepository.findByShortUrl("shortUrl")).thenReturn(urlEntity);

        urlService.incrementAccessCount("shortUrl");

        assertEquals("1", urlEntity.getSearchUrl());
        verify(urlRepository).save(urlEntity);
    }

    @Test
    public void testFindByShortUrl() {
        UrlEntity urlEntity = new UrlEntity("http://binance.com", "shortUrl", LocalDate.now(), "0");
        when(urlRepository.findByShortUrl("shortUrl")).thenReturn(urlEntity);

        UrlEntity result = urlService.findByShortUrl("shortUrl");

        assertEquals("shortUrl", result.getShortUrl());
    }

    @Test
    public void testFindByShortUrlAndDate() {
        UrlEntity urlEntity = new UrlEntity("http://telegram.com", "shortUrl", LocalDate.now(), "0");
        when(urlRepository.findByShortUrlAndLocalDate("shortUrl", LocalDate.now())).thenReturn(Optional.of(urlEntity));

        Optional<UrlEntity> result = urlService.findByShortUrlAndDate("shortUrl", LocalDate.now());

        assertEquals(urlEntity, result.get());
    }
}
