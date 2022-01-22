package com.example.folksdevurlshortener.controller;


import com.example.folksdevurlshortener.dto.ShortUrlDto;
import com.example.folksdevurlshortener.dto.converter.ShortUrlDtoConverter;
import com.example.folksdevurlshortener.model.ShortUrl;
import com.example.folksdevurlshortener.request.ShortUrlRequest;
import com.example.folksdevurlshortener.request.converter.ShortUrlRequestConverter;
import com.example.folksdevurlshortener.service.ShortUrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping
public class UrlController {

    private final ShortUrlDtoConverter shortUrlDtoConverter;
    private final ShortUrlRequestConverter shortUrlRequestConverter;
    private final ShortUrlService service;


    public UrlController(ShortUrlDtoConverter shortUrlDtoConverter,
                         ShortUrlRequestConverter shortUrlRequestConverter,
                         ShortUrlService service) {
        this.shortUrlDtoConverter = shortUrlDtoConverter;
        this.shortUrlRequestConverter = shortUrlRequestConverter;
        this.service = service;
    }


    @GetMapping("/all")
    public ResponseEntity<List<ShortUrlDto>> getAllUrls() {
        return ResponseEntity.ok(shortUrlDtoConverter.convertToDto(service.getAllShortUrl()));
    }


    @GetMapping("/show/{code}")
    public ResponseEntity<ShortUrlDto> getUrlByCode(@Valid @NotEmpty @PathVariable String code) {
        return ResponseEntity.ok(shortUrlDtoConverter.convertToDto(service.getUrlByCode(code)));
    }



    @GetMapping("/{code}")
    public ResponseEntity<ShortUrlDto> redirect(@Valid @NotEmpty @PathVariable String code) throws URISyntaxException {
        ShortUrl shortUrl = service.getUrlByCode(code);
        URI uri = new URI(shortUrl.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(httpHeaders).build();
    }


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ShortUrlRequest shortUrlRequest){
        ShortUrl createdShortUrl = service.create(shortUrlRequestConverter.convertToEntity(shortUrlRequest));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}")
                .buildAndExpand(createdShortUrl.getCode()).toUri();
        return ResponseEntity.created(location).body(shortUrlDtoConverter.convertToDto(createdShortUrl));
    }



}
