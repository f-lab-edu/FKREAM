package com.flab.fkream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class FkreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(FkreamApplication.class, args);
	}

}
