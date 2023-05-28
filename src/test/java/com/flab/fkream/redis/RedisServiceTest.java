package com.flab.fkream.redis;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    void getAddressId() throws InterruptedException {

        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);

        List<Thread> workers = new ArrayList<>();
        for (Long i = 0L; i < threadCount; i++) {
            workers.add(new Thread(new CountWorker(latch, i)));
        }
        workers.forEach(Thread::start);
        latch.await();

        Long addressId = redisService.getAddressId();
        System.out.println("addressId = " + addressId);
    }

    private class CountWorker implements Runnable {

        private CountDownLatch countDownLatch;
        private Long id;

        public CountWorker(CountDownLatch countDownLatch, Long id) {
            this.countDownLatch = countDownLatch;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                Long addressId = redisService.getAddressId();
                System.out.println("Thread id = " + id + " addressId = " + addressId);
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
