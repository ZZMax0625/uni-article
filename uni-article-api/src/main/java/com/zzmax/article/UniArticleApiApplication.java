package com.zzmax.article;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zzmax.article.mapper")
public class UniArticleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniArticleApiApplication.class, args);
    }

}
