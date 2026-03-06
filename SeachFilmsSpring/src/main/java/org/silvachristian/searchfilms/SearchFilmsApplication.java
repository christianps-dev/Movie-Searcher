package org.silvachristian.searchfilms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SearchFilmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchFilmsApplication.class, args);
    }

    @Bean
    public org.springframework.web.client.RestClient.Builder restClientBuilder() {
        return org.springframework.web.client.RestClient.builder();
    }

}
