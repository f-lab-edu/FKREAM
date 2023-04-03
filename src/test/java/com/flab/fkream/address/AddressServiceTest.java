package com.flab.fkream.address;

import static org.mockito.BDDMockito.*;

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
        doNothing().when(addressMapper).save(addressInfo);
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
        doNothing().when(addressMapper).update(addressInfo);
        addressService.update(addressInfo);
        then(addressMapper).should().update(addressInfo);
    }

    @Test
    void delete() {
        doNothing().when(addressMapper).delete(1L);
        addressService.delete(1L);
        then(addressMapper).should().delete(1L);
    }
}