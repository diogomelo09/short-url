package com.api.shortener_url.controller;

import com.api.shortener_url.controller.dto.UrlRequest;
import com.api.shortener_url.controller.dto.UrlResponse;
import com.api.shortener_url.entities.UrlEntity;
import com.api.shortener_url.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/url")
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest request, HttpServletRequest servletRequest) {
        UrlResponse response = urlService.addNewUrl(request, servletRequest);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("{shortUrl}")
    public ResponseEntity<Void> redirectById(@PathVariable("shortUrl") String id) {
        UrlEntity url = urlService.findByShortUrl(id);

        if (url.getFullUrl().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        urlService.incrementAccessCount(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.getFullUrl()));
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    @GetMapping("/access/{shortUrl}")
    public ResponseEntity<Integer> getAccessCountForShortUrl(@PathVariable("shortUrl") String shortUrl) {
        UrlEntity urlEntity = urlService.findByShortUrl(shortUrl);

        if (urlEntity != null) {
            String searchUrl = urlEntity.getSearchUrl();

            int accessCount = searchUrl != null && !searchUrl.isEmpty() ? Integer.parseInt(searchUrl) : 0;
            return ResponseEntity.ok(accessCount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/access/{shortUrl}/date/{localDate}")
    public ResponseEntity<String> getAccessCountByUrlAndDate(@PathVariable("shortUrl") String shortUrl,
                                                             @PathVariable("localDate")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {

        Optional<UrlEntity> urlEntity = urlService.findByShortUrlAndDate(shortUrl, localDate);

        return urlEntity.map(entity -> ResponseEntity.ok(entity.getSearchUrl())).orElseGet(()
                -> ResponseEntity.notFound().build());
    }
}

