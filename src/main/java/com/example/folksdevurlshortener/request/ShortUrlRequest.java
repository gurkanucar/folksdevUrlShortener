package com.example.folksdevurlshortener.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShortUrlRequest {

    @NotNull
    @NotEmpty
    private String url;

    private String code;

}
