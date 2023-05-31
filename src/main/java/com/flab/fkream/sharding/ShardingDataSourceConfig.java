package com.flab.fkream.sharding;


import com.flab.fkream.replication.RoutingDataSource;
import com.flab.fkream.sharding.ShardingDataSourceProperty.Property;
import com.flab.fkream.sharding.ShardingDataSourceProperty.Shard;
import com.zaxxer.hikari.HikariDataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.Setter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ConfigurationProperties(prefix = "datasource")
@Setter
public class ShardingDataSourceConfig {

    private ShardingDataSourceProperty property;

    private static final String SHARD_DELIMITER = "SHARD_DELIMITER";

    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private static final String MAPPER_LOCATION = "classpath:mybatis/mapper/*.xml";

    @Bean
    public DataSourceRouter shardingDataSource() {

        Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
        DataSourceRouter router = new DataSourceRouter();

        for (int i = 0; i < property.getShards().size(); i++) {
            Shard shard = property.getShards().get(i);
            Property master = shard.getMaster();
            Property slave = shard.getSlave();
            DataSource masterSource = DataSourceBuilder
                .create()
                .driverClassName(DRIVER_CLASS_NAME)
                .username(master.getUsername())
                .password(master.getPassword())
                .url(master.getUrl())
                .type(HikariDataSource.class)
                .build();

            DataSource slaveSource = DataSourceBuilder
                .create()
                .driverClassName(DRIVER_CLASS_NAME)
                .username(slave.getUsername())
                .password(slave.getPassword())
                .url(slave.getUrl())
                .type(HikariDataSource.class)
                .build();

            dataSourceMap.put(i + SHARD_DELIMITER + shard.getMaster().getName(), masterSource);
            dataSourceMap.put(i + SHARD_DELIMITER + shard.getSlave().getName(), slaveSource);
        }

        router.setTargetDataSources(dataSourceMap);
        router.afterPropertiesSet();

        return router;
    }

    @Bean
    public LazyConnectionDataSourceProxy lazyDataSource(DataSourceRouter dataSourceRouter) {
        return new LazyConnectionDataSourceProxy(dataSourceRouter);
    }

    @Bean
    public PlatformTransactionManager transactionManager(
        LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy) {
        return new DataSourceTransactionManager(lazyConnectionDataSourceProxy);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(
        LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy)
        throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(lazyConnectionDataSourceProxy);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
