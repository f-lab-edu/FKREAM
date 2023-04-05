package com.flab.fkream.salesAccount;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.flab.fkream.user.User;
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

	User user = User.builder()
		.email("test@test.com")
		.password("testpassword")
		.fourteenAgreement(true)
		.adAgreement(true)
		.personalAuthentication(true)
		.gender("Male")
		.phoneNumber("01012345678")
		.name("testuser")
		.build();

	SalesAccount salesAccount = SalesAccount.builder()
		.user(user)
		.bankName("Test Bank")
		.accountNumber("123-456-7890")
		.accountHolder("Test User")
		.build();

	@Test
	void save() {
		//given
		given(salesAccountMapper.save(salesAccount)).willReturn(1);

		//when
		int result = salesAccountService.save(salesAccount);

		//then
		//mock 객체에서 save() 메서드가 호출되었는지 검증
		then(salesAccountMapper).should().save(ArgumentMatchers.any(SalesAccount.class));
		Assertions.assertThat(result).isEqualTo(1);
	}

	@Test
	void findById() {
		//given
		given(salesAccountMapper.findById(anyLong())).willReturn(salesAccount);

		//when
		SalesAccount result = salesAccountService.findById(1L);

		//then
		then(salesAccountMapper).should().findById(ArgumentMatchers.anyLong());
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
			.id(1L)
			.user(user)
			.bankName("Test Bank2")
			.accountNumber("123-456-7890")
			.accountHolder("Test User")
			.createdAt(LocalDateTime.now())
			.build();
		given(salesAccountMapper.update(any(SalesAccount.class))).willReturn(1);

		//when
		int result = salesAccountService.update(updatedSalesAccount);

		//then
		then(salesAccountMapper).should().update(ArgumentMatchers.any(SalesAccount.class));
		Assertions.assertThat(result).isEqualTo(1);
	}

	@Test
	void deleteById() {
		//given
		given(salesAccountMapper.deleteById(anyLong())).willReturn(1);

		//when
		int result = salesAccountService.deleteById(1L);

		//then
		then(salesAccountMapper).should().deleteById(ArgumentMatchers.anyLong());
		Assertions.assertThat(result).isEqualTo(1);
	}
}