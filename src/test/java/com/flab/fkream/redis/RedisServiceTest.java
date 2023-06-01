package com.flab.fkream.redis;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    void getAddressId() throws InterruptedException, ExecutionException {

        int threadCount = 10;
        Set<Long> results = new HashSet<>();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<Long>> futures = new ArrayList<>();

        redisService.initAddressId();

        for (int i = 0; i < threadCount; i++) {
            Callable<Long> callable = () -> {
                Long addressId = redisService.getAddressId();
                return addressId;
            };
            futures.add(executorService.submit(callable));
        }
        executorService.shutdown();
        for (Future<Long> future : futures) {
            results.add(future.get());
        }

        assertThat(results.size()).isEqualTo(10);
        assertThat(redisService.getAddressId()).isEqualTo(11L);
    }
}
