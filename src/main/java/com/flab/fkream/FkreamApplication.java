package com.flab.fkream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
@EnableMongoRepositories
@ConfigurationPropertiesScan(value = "com.flab.fkream.sharding")
@Slf4j
public class FkreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(FkreamApplication.class, args);
	}


}
