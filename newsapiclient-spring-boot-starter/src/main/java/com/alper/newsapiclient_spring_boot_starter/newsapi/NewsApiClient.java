package com.alper.newsapiclient_spring_boot_starter.newsapi;


import com.alper.newsapiclient_spring_boot_starter.config.NewsApiClientSpringBootStarterProperties;
import com.alper.newsapiclient_spring_boot_starter.newsapi.dtos.Article;
import com.alper.newsapiclient_spring_boot_starter.newsapi.dtos.NewsApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class NewsApiClient {
    private NewsApiClientSpringBootStarterProperties properties;
    private RestClient restClient;
    private NewsApiUrlUtils urlUtils;


    @Autowired
    public NewsApiClient(NewsApiClientSpringBootStarterProperties properties) throws Exception {
        this.properties = properties;
        if(properties.apiKey().equals(null) || properties.baseUrl().equals(null)){
            throw  new Exception("STARTER_FAIL - Api key or base url could not be null.");
        }
        restClient = RestClient.create(properties.baseUrl());
        urlUtils = new NewsApiUrlUtils();
    }

    public NewsApiResponse requestNews(String subject, String dateFrom,String lang){

        return  restClient.get()
                .uri(urlUtils.newsUrl(subject,dateFrom, properties.apiKey(), lang))
                .retrieve()
                .body(NewsApiResponse.class);
    }

    public List<Article> getArticles(String subject, String dateFrom,String lang){
        NewsApiResponse  response = restClient.get()
                .uri(urlUtils.newsUrl(subject,dateFrom, properties.apiKey(),lang))
                .retrieve()
                .body(NewsApiResponse.class);

        return response.articles();
    }
}
