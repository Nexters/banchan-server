package com.banchan.config.question;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MimeTypeConfig {

    @Bean
    public Tika tika(){
        return new Tika();
    }
}
