package com.flab.fkream.aop;


import com.flab.fkream.error.exception.LoginRequiredException;
import com.flab.fkream.utils.SessionUtil;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Aspect
@Component
@Log4j2
public class AuthCheckAspect {

    @Before("@annotation(com.flab.fkream.aop.LoginCheck) && @annotation(loginCheck)")
    public void loginCheck(JoinPoint joinPoint, LoginCheck loginCheck) {
        if (isUserType(loginCheck)) {
            if (!isUserLoggedIn()) {
                throw new LoginRequiredException();
            }
        }
    }

    private boolean isUserType(LoginCheck loginCheck) {
        return LoginCheck.UserType.USER.equals(loginCheck.type());
    }

    private boolean isUserLoggedIn() {
        return SessionUtil.getLoginUserId() != null;
    }
}
