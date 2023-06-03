package com.flab.fkream.sharding;

import javax.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sharding")
@Setter
public class AddressShardingConfig {

    private ShardingProperty address;

    @PostConstruct
    public void init() {
        ShardingConfig.getShardingPropertyMap().put(ShardingTarget.ADDRESS, address);
    }

}
