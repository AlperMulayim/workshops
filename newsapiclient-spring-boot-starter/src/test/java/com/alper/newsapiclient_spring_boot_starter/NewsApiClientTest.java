package com.alper.newsapiclient_spring_boot_starter;

import com.alper.newsapiclient_spring_boot_starter.config.NewsApiClientConfiguration;
import com.alper.newsapiclient_spring_boot_starter.config.NewsApiClientSpringBootStarterProperties;
import com.alper.newsapiclient_spring_boot_starter.newsapi.NewsApiClient;
import com.alper.newsapiclient_spring_boot_starter.newsapi.NewsApiUrlUtils;
import com.alper.newsapiclient_spring_boot_starter.newsapi.dtos.Article;
import com.alper.newsapiclient_spring_boot_starter.newsapi.dtos.NewsApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class NewsApiClientTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                    NewsApiClientConfiguration.class,
                    RestClientAutoConfiguration.class,
                    NewsApiUrlUtils.class
            ));

    @Test
    public void shouldInitializedClientBean(){
        contextRunner.run(context ->{
            assertTrue(context.containsBean("newsApiClient"));
        });
    }

    @Test
    public void whenApiKeyInvalidThrowException(){
        contextRunner.run(context ->{
            NewsApiClientSpringBootStarterProperties properties =
                    new NewsApiClientSpringBootStarterProperties(null,null);
            assertThrows(Exception.class, ()-> new NewsApiClient(properties));

        });
    }

    @Test
    public void shouldInitializedPropertyWithDefaultUrl(){
        contextRunner.run(context -> {
            NewsApiClientSpringBootStarterProperties properties = context.getBean(NewsApiClientSpringBootStarterProperties.class);
            assertEquals(properties.baseUrl(),"https://newsapi.org/v2/");
        });
    }

//    @Test
//    public void shouldIncludeTheNews(){
//        contextRunner.run(context -> {
//            NewsApiClient client = context.getBean(NewsApiClient.class);
//            NewsApiResponse response = client.requestNews("bitcoin","2025-02-11","tr");
//            assertNotNull(response);
//        });
//    }

//    @Test
//    public void shouldNewsResponseStatusOk(){
//        contextRunner.run(context -> {
//            NewsApiClient client = context.getBean(NewsApiClient.class);
//            NewsApiResponse response = client.requestNews("bitcoin","2025-02-11","tr");
//            System.out.println(response.totalResults());
//            assertEquals("ok",response.status());
//        });
//    }

//    @Test
//    public void shouldArticlesNotNull(){
//        contextRunner.run(context -> {
//            NewsApiClient client = context.getBean(NewsApiClient.class);
//            List<Article> articles = client.getArticles();
//            assertNotNull(articles);
//        });
//    }

//    @Test
//    public void shouldAllArticlesHaveTitle(){
//        contextRunner.run(context -> {
//            NewsApiClient client = context.getBean(NewsApiClient.class);
//            List<Article> articles = client.getArticles("bitcoin","2025-02-11","tr");
//            assertThat(articles).allSatisfy(article -> {
//               assertNotNull(article.title());
//            });
//        });
//    }
}
