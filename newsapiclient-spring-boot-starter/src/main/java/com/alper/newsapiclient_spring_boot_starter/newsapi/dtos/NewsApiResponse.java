package com.alper.newsapiclient_spring_boot_starter.newsapi.dtos;

import java.util.List;

public record NewsApiResponse (
        String status,
        Integer totalResults,
        List<Article> articles
){}
