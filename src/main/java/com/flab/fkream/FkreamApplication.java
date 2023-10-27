package com.flab.fkream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FkreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(FkreamApplication.class, args);
    }

}
