package com.flab.fkream.sharding;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShardingProperty {

    private ShardingStrategy strategy;
    private List<ShardingRule> rules;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShardingRule {

        private int shardNo;
        private long rangeMin;
        private long rangeMax;
    }
}
