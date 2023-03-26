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

    @Before("@annotation(com.flab.fkream.aop.LoginCheck) && @annotation(loginCheck)")
    public void loginCheck(JoinPoint joinPoint, LoginCheck loginCheck) throws Throwable{
        log.debug("aop - login check");

        HttpSession session =((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();

        if (LoginCheck.UserType.USER.equals(loginCheck.type())){
            Long loginUserId = SessionUtil.getLoginUserId(session);
            if (loginUserId==null){
                throw new NoLoginException("로그인이 필요합니다.");
            }
        }



    }


}
