package com.zagbor.click;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.zagbor.click.repository")
@EntityScan(basePackages = "com.zagbor.click.model")
public class ClickApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickApplication.class, args);
    }

}
