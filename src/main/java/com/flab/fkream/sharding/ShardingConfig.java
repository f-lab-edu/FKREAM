package com.flab.fkream.sharding;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Setter;

@Setter
public class ShardingConfig {

    private static Map<ShardingTarget, ShardingProperty> shardingPropertyMap = new ConcurrentHashMap<>();


    public static Map<ShardingTarget, ShardingProperty> getShardingPropertyMap() {
        return shardingPropertyMap;
    }
}
