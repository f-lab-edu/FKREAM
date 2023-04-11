package com.flab.fkream.salesAccount;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SalesAccountServiceTest {

    @Mock
    SalesAccountMapper salesAccountMapper;

    @InjectMocks
    SalesAccountService salesAccountService;

    SalesAccount salesAccount = SalesAccount.builder()
        .id(1L)
        .userId(1L)
        .bankName("Test Bank")
        .accountNumber("123-456-7890")
        .accountHolder("Test User")
        .build();

    @Test
    void save() {
        //given
        given(salesAccountMapper.save(salesAccount)).willReturn(1);

        //when
        salesAccountService.save(salesAccount);

        //then
        then(salesAccountMapper).should().save(ArgumentMatchers.any(SalesAccount.class));
    }

    @Test
    void findById() {
        //given
        given(salesAccountMapper.findById(1L)).willReturn(salesAccount);

        //when
        SalesAccount result = salesAccountService.findById(1L);

        //then
        Assertions.assertThat(result).isEqualTo(salesAccount);
    }

    @Test
    void findAll() {
        //given
        List<SalesAccount> salesAccounts = new ArrayList<>();
        salesAccounts.add(salesAccount);
        given(salesAccountMapper.findAll()).willReturn(salesAccounts);

        //when
        List<SalesAccount> result = salesAccountService.findAll();

        //then
        then(salesAccountMapper).should().findAll();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).isEqualTo(salesAccount);
    }

    @Test
    void update() {
        //given
        SalesAccount updatedSalesAccount = SalesAccount.builder()
            .id(salesAccount.getId())
            .userId(1L)
            .bankName("Test Bank2")
            .accountNumber("123-456-7890")
            .accountHolder("Test User")
            .createdAt(LocalDateTime.now())
            .build();
        given(salesAccountMapper.update(any(SalesAccount.class))).willReturn(1);

        //when
        salesAccountService.update(updatedSalesAccount);

        //then
        then(salesAccountMapper).should().update(ArgumentMatchers.any(SalesAccount.class));
    }

    @Test
    void deleteById() {
        //given
        given(salesAccountMapper.deleteById(anyLong())).willReturn(1);

        //when
        salesAccountService.deleteById(1L);

        //then
        then(salesAccountMapper).should().deleteById(1L);
    }
}