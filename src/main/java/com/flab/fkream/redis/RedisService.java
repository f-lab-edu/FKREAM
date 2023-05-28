package com.flab.fkream.redis;

import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisService {

    @Resource(name = "redisValueOperations")
    private ValueOperations<String, Object> valueOps;

    public Long getAddressId() {
        Long increment = 0L;
        try {
            increment = valueOps.increment("spring:redis:getAddressId", 1L);
        } catch (Exception e) {
            log.info(e.toString());
        }
        return increment;
    }

}
