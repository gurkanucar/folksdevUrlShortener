package com.example.folksdevurlshortener.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShortUrlDto {

    private Long id;
    private String url;
    private String code;

}

