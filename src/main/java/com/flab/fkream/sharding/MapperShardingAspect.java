package com.flab.fkream.sharding;


import com.flab.fkream.user.User;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class MapperShardingAspect {

    @Around("@annotation(com.flab.fkream.sharding.Sharding) && @annotation(sharding) && args(shardKey,..)")
    public Object handler(ProceedingJoinPoint joinPoint, Sharding sharding, long shardKey)
        throws Throwable {
        UserHolder.setSharding(sharding.target(), shardKey);
        Object returnVal = joinPoint.proceed();
        UserHolder.clearSharding();
        return returnVal;
    }

}
