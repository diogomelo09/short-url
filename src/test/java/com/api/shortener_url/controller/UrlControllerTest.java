package com.api.shortener_url.controller;

import com.api.shortener_url.controller.dto.UrlRequest;
import com.api.shortener_url.controller.dto.UrlResponse;
import com.api.shortener_url.entities.UrlEntity;
import com.api.shortener_url.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UrlControllerTest {

    @Mock
    private UrlService urlService;

    @InjectMocks
    private UrlController urlController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShortenUrl() {
        UrlRequest request = new UrlRequest("http://example.com");
        UrlResponse response = new UrlResponse("shortUrl");
        when(urlService.addNewUrl(any(UrlRequest.class), any(HttpServletRequest.class))).thenReturn(response);

        ResponseEntity<UrlResponse> result = urlController.shortenUrl(request, mock(HttpServletRequest.class));

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("shortUrl", result.getBody().getRedirectUrl());
    }

    @Test
    public void testRedirectById() {
        UrlEntity urlEntity = new UrlEntity("http://google.com", "shortUrl", LocalDate.now(), "0");
        when(urlService.findByShortUrl("shortUrl")).thenReturn(urlEntity);

        ResponseEntity<Void> result = urlController.redirectById("shortUrl");

        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

    @Test
    public void testGetAccessCountByUrl() {
        UrlEntity urlEntity = new UrlEntity("http://facebook.com", "shortUrl", LocalDate.now(), "5");
        when(urlService.findByShortUrl("shortUrl")).thenReturn(urlEntity);

        ResponseEntity<Integer> result = urlController.getAccessCountForShortUrl("shortUrl");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(5, result.getBody());
    }

    @Test
    public void testGetAccessCountByUrlAndDate() {
        UrlEntity urlEntity = new UrlEntity("http://github.com", "shortUrl", LocalDate.now(), "10");
        when(urlService.findByShortUrlAndDate("shortUrl", LocalDate.now())).thenReturn(Optional.of(urlEntity));

        ResponseEntity<String> result = urlController.getAccessCountByUrlAndDate("shortUrl", LocalDate.now());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("10", result.getBody());
    }
}
