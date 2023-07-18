package com.flab.fkream.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.redisson.host}")
    private String redissonHost;
    @Value("${spring.redis.redisson.port}")
    private int redissonPort;

    private static final String REDISSON_HOST_PREFIX = "redis://";

    @Bean
    public RedissonClient redissonClient() {
        RedissonClient redisson = null;
        Config config = new Config();
        config.useSingleServer()
            .setAddress(REDISSON_HOST_PREFIX + redissonHost + ":" + redissonPort);
        redisson = Redisson.create(config);
        return redisson;
    }
}
