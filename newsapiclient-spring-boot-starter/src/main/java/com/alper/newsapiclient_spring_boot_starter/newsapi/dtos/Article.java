package com.alper.newsapiclient_spring_boot_starter.newsapi.dtos;


import java.util.Date;

public record Article (
     Source source,
     String author,
     String title,
     String description,
     String url,
     String urlToImage,
     Date publishedAt,
     String content
){}
