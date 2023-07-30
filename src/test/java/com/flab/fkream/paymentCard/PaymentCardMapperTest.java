package com.flab.fkream.paymentCard;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.fkream.mapper.PaymentCardMapper;
import com.flab.fkream.user.User;
import com.flab.fkream.mapper.UserMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles({"test"})
class PaymentCardMapperTest {

    @Autowired
    PaymentCardMapper paymentCardMapper;

    @Autowired
    UserMapper userMapper;

    User user;

    PaymentCard paymentCard;

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
        paymentCard = PaymentCard.builder()
            .userId(user.getId())
            .cardCompany("woori")
            .cardNumber("1111")
            .expiration("12/32")
            .cardPw("1234")
            .build();
    }

    @Test
    void save() {
        assertThat(paymentCardMapper.save(paymentCard)).isEqualTo(1);
    }

    @Test
    void findByUserId() {
        paymentCardMapper.save(paymentCard);
        List<PaymentCard> paymentCards = paymentCardMapper.findByUserId(user.getId());
        assertThat(paymentCards).contains(paymentCard);
    }

    @Test
    void findOne() {
        paymentCardMapper.save(paymentCard);
        assertThat(paymentCardMapper.findById(paymentCard.getId())).isEqualTo(paymentCard);
    }

    @Test
    void delete() {
        paymentCardMapper.save(paymentCard);
        assertThat(paymentCardMapper.delete(paymentCard.getId())).isEqualTo(1);
    }
}