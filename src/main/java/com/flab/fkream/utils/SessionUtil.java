package com.flab.fkream.utils;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static final String LOGIN_USERS_ID = "LOGIN_USERS_ID";

    public static final String LOGIN_MANAGER_ID = "LOGIN_MANAGER_ID";


    //인스턴스화 방지
    private SessionUtil(){}


    public static void setLoginUserId(HttpSession session, Long id){
        session.setAttribute(LOGIN_USERS_ID, id);
    }

    public static String getLoginUserId(HttpSession session, String id){
        return (String) session.getAttribute(LOGIN_USERS_ID);
    }


}
