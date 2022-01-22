package com.example.folksdevurlshortener.request.converter;

import com.example.folksdevurlshortener.model.ShortUrl;
import com.example.folksdevurlshortener.request.ShortUrlRequest;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlRequestConverter {

    public ShortUrl convertToEntity(ShortUrlRequest shortUrlRequest) {
        return ShortUrl.builder()
                .url(shortUrlRequest.getUrl())
                .code(shortUrlRequest.getCode().toUpperCase())
                .build();
    }

}
