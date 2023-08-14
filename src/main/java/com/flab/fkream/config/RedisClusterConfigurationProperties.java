package com.flab.fkream.config;


import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "redis.cluster")
public class RedisClusterConfigurationProperties {

    List<String> nodes;
}
