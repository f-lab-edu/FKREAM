package com.flab.fkream.salesAccount;

import static org.assertj.core.api.Assertions.*;

import com.flab.fkream.user.User;
import com.flab.fkream.user.UserMapper;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class SalesAccountMapperTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SalesAccountMapper salesAccountMapper;
    User user;
    SalesAccount salesAccount;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .email("test1")
            .password("000")
            .gender("aa")
            .name("test")
            .adAgreement(true)
            .fourteenAgreement(true)
            .personalAuthentication(true)
            .phoneNumber("010")
            .build();
        userMapper.save(user);
        salesAccount = SalesAccount.builder()
            .userId(user.getId())
            .bankName("woori")
            .accountNumber("11-1234-123321")
            .accountHolder("testHolder")
            .build();
    }

    @Test
    public void testSave() {
        assertThat(salesAccountMapper.save(salesAccount)).isEqualTo(1);
    }


    @Test
    public void testFindById() {
        // given
        salesAccountMapper.save(salesAccount);

        // when
        SalesAccount found = salesAccountMapper.findByUserId(salesAccount.getUserId());

        // then
        assertThat(found).isEqualTo(salesAccount);
    }

    @Test
    public void testFindAll() {
        // given
        findAllSetup();

        //when
        List<SalesAccount> all = salesAccountMapper.findAll();

        //then
        assertThat(all.size()).isEqualTo(2);
    }

    private void findAllSetup() {
        User user1 = User.builder()
            .email("test1@test.com")
            .password("testpassword1")
            .fourteenAgreement(true)
            .adAgreement(true)
            .personalAuthentication(true)
            .gender("M")
            .phoneNumber("01011111111")
            .name("testuser1")
            .build();

        User user2 = User.builder()
            .email("test2@test.com")
            .password("testpassword2")
            .fourteenAgreement(true)
            .adAgreement(true)
            .personalAuthentication(true)
            .gender("M")
            .phoneNumber("01022222222")
            .name("testuser2")
            .build();

        userMapper.save(user1);
        userMapper.save(user2);

        SalesAccount salesAccount1 = SalesAccount.builder()
            .userId(user1.getId())
            .bankName("Test Bank1")
            .accountNumber("123-456-7891")
            .accountHolder("Test User1")
            .build();

        SalesAccount salesAccount2 = SalesAccount.builder()
            .userId(user2.getId())
            .bankName("Test Bank2")
            .accountNumber("123-456-7892")
            .accountHolder("Test User2")
            .build();

        salesAccountMapper.save(salesAccount1);
        salesAccountMapper.save(salesAccount2);
    }

    @Test
    void testUpdate() {
        // given
        salesAccountMapper.save(salesAccount);

        //when
        SalesAccount salesAccountUpdated = SalesAccount.builder()
            .id(salesAccount.getId())
            .userId(user.getId())
            .bankName("New Test Bank")
            .accountNumber("123-456-7890")
            .accountHolder("Test User")
            .build();
        salesAccountMapper.update(salesAccountUpdated);

        //then
        assertThat(salesAccountMapper.findByUserId(salesAccount.getUserId())).isEqualTo(
            salesAccountUpdated);
    }

    @Test
    void testDeleteById() {
        //when
        salesAccountMapper.deleteByUserId(salesAccount.getUserId());

        //then
        assertThat(salesAccountMapper.findByUserId(salesAccount.getUserId())).isNull();
    }

    @Test
    void testFailDeleteById() {
        //when
        salesAccountMapper.deleteByUserId(2L);

        //then
        assertThat(salesAccountMapper.findByUserId(salesAccount.getUserId())).isNull();
    }

}