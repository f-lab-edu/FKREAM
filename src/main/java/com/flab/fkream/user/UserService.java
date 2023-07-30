package com.flab.fkream.user;

import com.flab.fkream.error.exception.DuplicatedEmailException;
import com.flab.fkream.error.exception.LoginFailureException;
import com.flab.fkream.error.exception.NoDataFoundException;
import com.flab.fkream.login.LoginForm;
import com.flab.fkream.mapper.UserMapper;
import com.flab.fkream.utils.SHA256Util;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final RedissonClient redissonClient;


    public void addUser(User user) {
        String lockName = generateLockName(user.getEmail());
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (!rLock.tryLock(0, TimeUnit.MILLISECONDS)) {
                throw new NoDataFoundException();
            }
            isDuplicatedEmail(user.getEmail());
            user.setPassword(SHA256Util.encrypt(user.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            userMapper.save(user);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            unlock(rLock);
        }
    }

    public User login(LoginForm loginForm) {
        loginForm.setPassword(SHA256Util.encrypt(loginForm.getPassword()));
        User user = userMapper.findByEmail(loginForm.getEmail());
        if (user == null) {
            throw new LoginFailureException();
        }
        if (user.getPassword().equals(loginForm.getPassword())) {
            return user;
        }
        throw new LoginFailureException();
    }

    private String generateLockName(String email) {
        return this.getClass() + email;
    }

    private void isDuplicatedEmail(String email) {
        User user = userMapper.findByEmail(email);
      if (user != null) {
        throw new DuplicatedEmailException();
      }
    }

    private void unlock(RLock rLock) {
        if (rLock != null && rLock.isLocked()) {
            rLock.unlock();
        }
    }
}
