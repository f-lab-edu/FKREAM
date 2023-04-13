package com.flab.fkream.utils;

import javax.servlet.http.HttpSession;

public class SessionUtil {

  private static final String LOGIN_USERS_ID = "LOGIN_USERS_ID";
  private static final String LOGIN_MANAGER_ID = "LOGIN_MANAGER_ID";

  //인스턴스화 방지
  private SessionUtil() {
  }

  public static void setLoginUserId(HttpSession session, Long id) {
    session.setAttribute(LOGIN_USERS_ID, id);
  }

  public static Long getLoginUserId(HttpSession session) {
    return (Long) session.getAttribute(LOGIN_USERS_ID);
  }

  public static Long getLoginUserId(){
    return (Long) HttpRequestUtils.getRequest().getSession().getAttribute(LOGIN_USERS_ID);
  }

  public static void logoutUser(HttpSession session) {
    session.removeAttribute(LOGIN_USERS_ID);
  }

}
