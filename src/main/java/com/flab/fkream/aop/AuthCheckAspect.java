package com.flab.fkream.aop;


import com.flab.fkream.error.exception.LoginFailureException;
import com.flab.fkream.utils.SessionUtil;
import javax.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Log4j2
public class AuthCheckAspect {

  @Before("@annotation(com.flab.fkream.aop.LoginCheck) && @annotation(loginCheck)")
  public void loginCheck(JoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
    log.debug("aop - login check");

    HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest()
        .getSession();

    if (LoginCheck.UserType.USER.equals(loginCheck.type())) {
      Long loginUserId = SessionUtil.getLoginUserId(session);
      if (loginUserId == null) {
        throw new LoginFailureException();
      }

    }
  }

}
