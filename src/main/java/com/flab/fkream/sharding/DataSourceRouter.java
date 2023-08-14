package com.flab.fkream.sharding;

import com.flab.fkream.constants.Constants;
import com.flab.fkream.error.exception.InvalidShardKeyException;
import com.flab.fkream.error.exception.ShardingStrategyNotFoundException;
import com.flab.fkream.sharding.ShardingProperty.ShardingRule;
import com.flab.fkream.sharding.UserHolder.Sharding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Log4j2
public class DataSourceRouter extends AbstractRoutingDataSource {

    private Map<Integer, MhaDataSource> shards;
    private static final String MASTER = "master";
    private static final String SLAVE = "slave";

    public int getShardSize() {
        return shards.size();
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

        shards = new HashMap<>();

        for (Object item : targetDataSources.keySet()) {
            String dataSourceName = item.toString();
            String shardNoStr = dataSourceName.split(Constants.ShardDelimiter)[0];

            MhaDataSource shard = getShard(shardNoStr);
            if (dataSourceName.contains(MASTER)) {
                shard.setMasterName(dataSourceName);
            } else if (dataSourceName.contains(SLAVE)) {
                shard.setSlaveName(dataSourceName);
            }
        }
    }

    @Override
    protected Object determineCurrentLookupKey() {
        Sharding sharding = UserHolder.getSharding();
        int shardNo = getShardNo(sharding);
        MhaDataSource dataSource = shards.get(shardNo);
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) log.info("readOnly");
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            ? dataSource.getSlaveName() : dataSource.getMasterName();
    }

    private int getShardNo(Sharding sharding) {
        if (sharding == null) {
            return 0;
        }

        // 전체 검색인 경우
        if (sharding.getShardNo() != null) {
            return sharding.getShardNo();
        }

        int shardNo = 0;
        ShardingProperty shardingProperty = ShardingConfig.getShardingPropertyMap()
            .get(sharding.getTarget());
        if (shardingProperty.getStrategy() == ShardingStrategy.RANGE) {
            shardNo = getShardNoByRange(shardingProperty.getRules(), sharding.getShardKey());
        }
        if (shardingProperty.getStrategy() != ShardingStrategy.RANGE) {
            throw new ShardingStrategyNotFoundException();
        }
        return shardNo;
    }

    private int getShardNoByRange(List<ShardingRule> rules, long shardKey) {
        for (ShardingRule rule : rules) {
            if (rule.getRangeMin() <= shardKey && shardKey <= rule.getRangeMax()) {
                return rule.getShardNo();
            }
        }
        throw new InvalidShardKeyException();
    }

    private MhaDataSource getShard(String shardNoStr) {
        int shardNo = 0;
        if (isNumeric(shardNoStr)) {
            shardNo = Integer.valueOf(shardNoStr);
        }

        MhaDataSource shard = shards.get(shardNo);
        if (shard == null) {
            shard = new MhaDataSource();
            shards.put(shardNo, shard);
        }
        return shard;
    }

    private boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Getter
    @Setter
    private class MhaDataSource {

        private String masterName;
        private String slaveName;
    }
}
