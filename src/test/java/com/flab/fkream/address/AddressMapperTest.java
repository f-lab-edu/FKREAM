package com.flab.fkream.address;

import static org.assertj.core.api.Assertions.*;

import com.flab.fkream.user.User;
import com.flab.fkream.user.UserMapper;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AddressMapperTest {

    @Autowired
    AddressMapper addressMapper;
    @Autowired
    UserMapper usersMapper;

    User user;

    Address addressInfo;

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
        usersMapper.save(user);
        addressInfo = Address.builder()
            .userId(user.getId())
            .name("test")
            .phoneNumber("010")
            .zipcode("3")
            .detail1("ag")
            .detail2("ads")
            .build();
    }

    @Test
    void save() {
        assertThat(addressMapper.save(addressInfo)).isEqualTo(1);

    }

    @Test
    void findOne() {
        addressMapper.save(addressInfo);
        assertThat(addressMapper.findOne(addressInfo.getId())).isEqualTo(addressInfo);
    }

    @Test
    void findOneByUserID() {
        addressMapper.save(addressInfo);
        List<Address> addresses = addressMapper.findByUserId(addressInfo.getUserId());
        assertThat(addresses.get(0)).isEqualTo(addressInfo);
    }

    @Test
    void update() {
        addressMapper.save(addressInfo);
        Address addressUpdateInfo = Address.builder()
            .id(addressInfo.getId())
            .userId(user.getId())
            .name("update")
            .phoneNumber("010")
            .zipcode("3")
            .detail1("ag")
            .detail2("ads")
            .build();

        addressMapper.update(addressUpdateInfo);
        Address result = addressMapper.findOne(addressInfo.getId());
        assertThat(result).isEqualTo(addressUpdateInfo);
    }

    @Test
    void delete() {
        addressMapper.save(addressInfo);
        assertThat(addressMapper.delete(addressInfo.getId())).isEqualTo(1);
    }
}