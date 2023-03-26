package com.flab.fkream.aop;


import com.flab.fkream.error.exception.NoLoginException;
import com.flab.fkream.utils.SessionUtil;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Aspect
@Component
@Log4j2
public class AuthCheckAspect {

    @Before("@annotation(com.flab.fkream.aop.LoginCheck)")
    public void loginCheck(JoinPoint joinPoint) throws Throwable{
        log.debug("aop - login check");

        HttpSession session =((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        String loginUserEmail = SessionUtil.getLoginUserId(session);

        if (loginUserEmail==null){
            throw new NoLoginException("로그인이 필요합니다.");
        }

    }


}
