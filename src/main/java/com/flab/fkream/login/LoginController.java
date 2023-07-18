package com.flab.fkream.login;

import static com.flab.fkream.aop.LoginCheck.UserType;

import com.flab.fkream.aop.LoginCheck;
import com.flab.fkream.user.User;
import com.flab.fkream.user.UserService;
import com.flab.fkream.utils.SessionUtil;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginForm loginForm, HttpSession httpSession) {
        User user = userService.login(loginForm);
        SessionUtil.setLoginUserId(httpSession, user.getId());
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @LoginCheck(type = UserType.USER)
    public void logout(HttpSession session) {
        SessionUtil.logoutUser(session);
    }
}
