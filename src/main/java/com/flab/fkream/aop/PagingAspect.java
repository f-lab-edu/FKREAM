package com.flab.fkream.aop;


import com.flab.fkream.address.Address;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2
public class PagingAspect {

    @Around("@annotation(com.flab.fkream.aop.Paging)")
    public PageInfo paging(ProceedingJoinPoint joinPoint) throws Throwable{
        int pageNum = (int)joinPoint.getArgs()[1];
        int pageSize = (int)joinPoint.getArgs()[2];
        if (pageNum==0 || pageSize ==0) {
            pageNum=1;
            pageSize=5;
        }
        PageHelper.startPage(pageNum, pageSize);
        Object result = joinPoint.proceed();
        List<Object> result1 = List.of(result);
        return PageInfo.of(result1);
    }
}
