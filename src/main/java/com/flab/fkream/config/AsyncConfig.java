package com.flab.fkream.config;


import java.util.concurrent.Executor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Log4j2
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "InstantPurchasePriceChangeEventAsync")
    public Executor InstantPurchasePriceChangeEventThreadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int processors = Runtime.getRuntime().availableProcessors();
        log.info("processors count {}",processors);
        executor.setThreadNamePrefix("InstantPurchasePriceChangeEventAsync-"); // thread 이름 설정
        executor.setCorePoolSize(processors); // 기본 스레드 수
        executor.setMaxPoolSize(processors*2); // 최대 스레드 개수
        executor.setQueueCapacity(50); // 최대 큐 수
        executor.setKeepAliveSeconds(60); // maxPoolSize로 인해 덤으로 더 돌아다니는 튜브는 60초 후에 수거해서 정리
        executor.initialize(); // 초기화후 반환
        return executor;
    }

    @Bean(name = "DealStatusChangeEventAsync")
    public Executor DealStatusChangeEventThreadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int processors = Runtime.getRuntime().availableProcessors();
        log.info("processors count {}",processors);
        executor.setThreadNamePrefix("DealStatusChangeEventAsync-"); // thread 이름 설정
        executor.setCorePoolSize(processors); // 기본 스레드 수
        executor.setMaxPoolSize(processors*2); // 최대 스레드 개수
        executor.setQueueCapacity(50); // 최대 큐 수
        executor.setKeepAliveSeconds(60); // maxPoolSize로 인해 덤으로 더 돌아다니는 튜브는 60초 후에 수거해서 정리
        executor.initialize(); // 초기화후 반환
        return executor;
    }


}
