package com.alper.newsapiclient_spring_boot_starter.newsapi;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component("newsUrlUtils")
public class NewsApiUrlUtils {

   public String newsUrl(String subject, String dateFrom, String apiKey, String lang){
       return UriComponentsBuilder.fromPath("/everything")
                .queryParam("q", subject)
                .queryParam("from", dateFrom)
                .queryParam("sortBy", "date")
                .queryParam("apiKey", apiKey)
                .queryParam("language", lang)
                .build()
                .toUriString();
    }
}
