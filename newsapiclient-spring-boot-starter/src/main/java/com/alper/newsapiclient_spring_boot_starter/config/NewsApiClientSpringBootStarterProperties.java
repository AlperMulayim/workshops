package com.alper.newsapiclient_spring_boot_starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties("newsapi-springboot-starter-properties")
public record NewsApiClientSpringBootStarterProperties (
    @DefaultValue("https://newsapi.org/v2/")
    String baseUrl,
    @DefaultValue("API-KEY")
    String apiKey
){}
