package com.flab.fkream.sharding;

import com.flab.fkream.address.Address;
import com.flab.fkream.address.AddressRepository;
import java.sql.Connection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ShardingTest {

    @SpyBean
    DataSourceRouter shardingDataSource;
    @Autowired
    AddressRepository addressRepository;


    @Test
    @Transactional
    void shardingTest() {
        Long id1 = 20L;
        Long id2 = 120L;
        addressRepository.addAddress(id1, Address.builder().id(id1).name("testAddress1").build());
        addressRepository.addAddress(id2, Address.builder().id(id2).name("testAddress2").build());



    }

}
