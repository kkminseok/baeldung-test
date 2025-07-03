package com.my.springbootkafkaapacheavrohandle;

import com.my.springbootkafkaapacheavrohandle.model.Article;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootKafkaApacheAvroHandleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootKafkaApacheAvroHandleApplication.class, args);
    }


    List<Article> blog = new ArrayList<>();

    @KafkaListener(topics = "minseok.article.published")
    public void listen(Article article) {
        System.out.println("a new article was published " + article);
        blog.add(article);
    }

}
