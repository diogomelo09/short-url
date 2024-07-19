package com.api.shortener_url.controller.dto;

public class UrlResponse {
    private final String redirectUrl;

    public UrlResponse(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

}
