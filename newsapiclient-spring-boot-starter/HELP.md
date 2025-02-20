```java
		<dependency>
			<groupId>com.alpermulayim</groupId>
			<artifactId>news-api-springboot-starter</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
```

## `NewsApiClient`  Methods.

```java
List<Article> getArticles(String subject, String dateFrom, String lang); 
NewsApiResponse requestNews(String subject, String dateFrom, String lang);

subject: query search parameter subject like < bitcoin, europe, breaking news.> 
date: format YYYY-MM-DD <2024-02-22> 
lang: language-code <tr,en,fr> 
```

## How to use starter news-api-springboot-starter in another Spring Boot Application

1. Build the starter code,
2. Classic importing steps for spring boot starters, we can use directly the weblient methods now.
3. With **news-api-springboot-starter**  we are able to call Rest Api of NewsApi without custom webclient  configurations and implementation. Easy to use and simple!

```java
		<dependency>
			<groupId>com.alpermulayim</groupId>
			<artifactId>news-api-springboot-starter</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
```

NewsApiClient need to NewsApiClientSpringBootStarterProperties with url and api key.

Please visit [https://newsapi.org](https://newsapi.org/) for creating new  API_KEY.

```java
@Configuration
public class NewsConfig {

    @Bean
    public NewsApiClient client() throws Exception{
        return new NewsApiClient( new NewsApiClientSpringBootStarterProperties(
        "https://newsapi.org/v2/",
        "API_KEY"));
    }
}

```

```java

//Demo use case we need to implement our service and request mapping :) 
@RestController
@RequestMapping("api/v1/public/news")
public class NewsController {

    private NewsApiClient newsApiClient;

    @Autowired
    public NewsController(NewsApiClient newsApiClient) {
        this.newsApiClient = newsApiClient;
    }

    @GetMapping
    public NewsApiResponse getAll(){
        return newsApiClient.requestNews("bitcoin","2025-02-01","en");
    }

    @GetMapping("/articles")
    public List<Article> getArticles(){
        return newsApiClient.getArticles("europe","2025-02-01","tr");
    }
}

```
