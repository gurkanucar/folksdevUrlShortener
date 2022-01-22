package com.example.folksdevurlshortener.service;

import com.example.folksdevurlshortener.exception.CodeAlreadyExists;
import com.example.folksdevurlshortener.exception.ShortUrlNotFoundException;
import com.example.folksdevurlshortener.model.ShortUrl;
import com.example.folksdevurlshortener.repository.ShortUrlRepository;
import com.example.folksdevurlshortener.util.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService {

    private final ShortUrlRepository repository;
    private final RandomStringGenerator randomStringGenerator;


    public ShortUrlService(ShortUrlRepository repository,
                           RandomStringGenerator randomStringGenerator) {
        this.repository = repository;
        this.randomStringGenerator = randomStringGenerator;
    }


    public List<ShortUrl> getAllShortUrl() {
        return repository.findAll();
    }


    public ShortUrl getUrlByCode(String code) {
        return repository.findAllByCode(code).orElseThrow(
                ()-> new ShortUrlNotFoundException("url not found!")
        );
    }

    public ShortUrl create(ShortUrl shortUrl) {
        if(shortUrl.getCode() ==null || shortUrl.getCode().isEmpty()){
            shortUrl.setCode(generateCode());
        }
        else if(repository.findAllByCode(shortUrl.getCode()).isPresent()){
            throw new CodeAlreadyExists("code already exists");
        }
        shortUrl.setCode(shortUrl.getCode().toUpperCase());
        return repository.save(shortUrl);
    }

    private String generateCode(){
        String code;

        do{
            code = randomStringGenerator.generateRandomString();
        }
        while (repository.findAllByCode(code).isPresent());

        return code;
    }


}
