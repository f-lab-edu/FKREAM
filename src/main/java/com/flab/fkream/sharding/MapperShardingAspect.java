package com.flab.fkream.sharding;


import com.flab.fkream.address.Address;
import com.flab.fkream.user.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class MapperShardingAspect {

    private final DataSourceRouter dataSourceRouter;

    @Around("@annotation(com.flab.fkream.sharding.Sharding) && @annotation(sharding) && args(shardKey,..)")
    public Object handler(ProceedingJoinPoint joinPoint, Sharding sharding, long shardKey)
        throws Throwable {
        UserHolder.setSharding(sharding.target(), shardKey);
        Object returnVal = joinPoint.proceed();
        UserHolder.clearSharding();
        return returnVal;
    }

    @Around("@annotation(com.flab.fkream.sharding.AllShardQuery)")
    public List<?> handleAllShardQuery(ProceedingJoinPoint joinPoint) throws Throwable {
        int shardSize = dataSourceRouter.getShardSize();
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < shardSize; i++) {
            UserHolder.setShardingWithShardNo(i);
            result.addAll((List<?>) joinPoint.proceed());
        }
        UserHolder.clearSharding();
        return result;
    }

}
