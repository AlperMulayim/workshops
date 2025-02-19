package com.alper.newsapiclient_spring_boot_starter.config;

import com.alper.newsapiclient_spring_boot_starter.newsapi.NewsApiClient;
import com.alper.newsapiclient_spring_boot_starter.newsapi.NewsApiUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;


@AutoConfiguration
@EnableConfigurationProperties(NewsApiClientSpringBootStarterProperties.class)
public class NewsApiClientConfiguration {
    private final NewsApiClientSpringBootStarterProperties apiProperties;
    private final NewsApiUrlUtils urlUtils;

    @Autowired
    public NewsApiClientConfiguration(NewsApiClientSpringBootStarterProperties properties, NewsApiUrlUtils urlUtils){
        this.apiProperties = properties;
        this.urlUtils = urlUtils;
    }

    @Bean(name = "newsApiClient")
    NewsApiClient newsApiClient() throws Exception{
        return new NewsApiClient(apiProperties);
    }
}
