package com.flab.fkream.address;

import static org.mockito.BDDMockito.*;

import com.flab.fkream.mapper.AddressMapper;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    AddressMapper addressMapper;

    @InjectMocks
    AddressService addressService;

    Address addressInfo = Address.builder()
        .userId(1L)
        .name("test")
        .phoneNumber("010")
        .zipcode("3")
        .detail1("ag")
        .detail2("ads")
        .build();

    @Test
    void addAddress() {
        given(addressMapper.save(addressInfo)).willReturn(1);
        addressService.addAddress(addressInfo);
        then(addressMapper).should().save(addressInfo);
    }

    @Test
    void findOne() {
        given(addressMapper.findOne(1L)).willReturn(addressInfo);
        Assertions.assertThat(addressService.findOne(1L)).isEqualTo(addressInfo);
    }

    @Test
    void findByUserId() {
        given(addressMapper.findByUserId(1L)).willReturn(List.of(addressInfo));
        Assertions.assertThat(addressService.findByUserId(1L)).hasSize(1);
    }

    @Test
    void update() {
        given(addressMapper.update(addressInfo)).willReturn(1);
        addressService.update(addressInfo);
        then(addressMapper).should().update(addressInfo);
    }

    @Test
    void delete() {
        given(addressMapper.delete(addressInfo.getId())).willReturn(1);
        addressService.delete(addressInfo.getId());
        then(addressMapper).should().delete(addressInfo.getId());
    }
}