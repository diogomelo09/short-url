package com.api.shortener_url.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Entity
@Table(name = "urls")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_url", nullable = false)
    private String fullUrl;

    @Column(name = "short_url", nullable = false, unique = true)
    private String shortUrl;

    @Column(name = "search_url")
    private String searchUrl;

    @Column(name = "local_date")
    private LocalDate localDate;


    public UrlEntity(String fullUrl, String shortUrl, LocalDate localDate, String searchUrl) {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.localDate = localDate;
        this.searchUrl = searchUrl;
    }


}


