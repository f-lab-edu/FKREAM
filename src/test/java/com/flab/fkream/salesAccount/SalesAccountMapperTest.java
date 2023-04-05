package com.flab.fkream.salesAccount;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import com.flab.fkream.users.Users;
import com.flab.fkream.users.UsersMapper;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class SalesAccountMapperTest {
	@Autowired
	UsersMapper usersMapper;

	@Autowired
	SalesAccountMapper salesAccountMapper;

	@Test
	public void testSave() {
		// given
		Users users = Users.builder()
			.email("test@test.com")
			.password("testpassword")
			.fourteenAgreement(true)
			.adAgreement(true)
			.personalAuthentication(true)
			.gender("Male")
			.phoneNumber("01012345678")
			.name("testuser")
			.build();

		usersMapper.save(users);

		SalesAccount salesAccount = SalesAccount.builder()
			.users(users)
			.bankName("Test Bank")
			.accountNumber("123-456-7890")
			.accountHolder("Test User")
			.createdAt(LocalDateTime.now())
			.build();

		// when
		salesAccountMapper.save(salesAccount);

		// then
		assertThat(salesAccount.getId()).isNotNull();
	}

	@Test
	public void testFindById() {
		// given
		Users users = Users.builder()
			.email("test@test.com")
			.password("testpassword")
			.fourteenAgreement(true)
			.adAgreement(true)
			.personalAuthentication(true)
			.gender("Male")
			.phoneNumber("01012345678")
			.rank(Users.Rank.BRONZE)
			.name("testuser")
			.build();
		usersMapper.save(users);

		SalesAccount salesAccount = SalesAccount.builder()
			.users(users)
			.bankName("Test Bank")
			.accountNumber("123-456-7890")
			.accountHolder("Test User")
			.build();

		salesAccountMapper.save(salesAccount);

		// when
		SalesAccount found = salesAccountMapper.findById(salesAccount.getId());

		// then
		assertThat(found).isEqualTo(salesAccount);
	}

	@Test
	public void testFindAll() {
		// given
		Users users1 = Users.builder()
			.email("test1@test.com")
			.password("testpassword1")
			.fourteenAgreement(true)
			.adAgreement(true)
			.personalAuthentication(true)
			.gender("M")
			.phoneNumber("01011111111")
			.name("testuser1")
			.build();

		Users users2 = Users.builder()
			.email("test2@test.com")
			.password("testpassword2")
			.fourteenAgreement(true)
			.adAgreement(true)
			.personalAuthentication(true)
			.gender("M")
			.phoneNumber("01022222222")
			.name("testuser2")
			.build();

		usersMapper.save(users1);
		usersMapper.save(users2);

		SalesAccount salesAccount1 = SalesAccount.builder()
			.users(users1)
			.bankName("Test Bank1")
			.accountNumber("123-456-7891")
			.accountHolder("Test User1")
			.build();

		SalesAccount salesAccount2 = SalesAccount.builder()
			.users(users2)
			.bankName("Test Bank2")
			.accountNumber("123-456-7892")
			.accountHolder("Test User2")
			.build();

		salesAccountMapper.save(salesAccount1);
		salesAccountMapper.save(salesAccount2);

		//when
		List<SalesAccount> all = salesAccountMapper.findAll();

		//then
		assertThat(all.size()).isEqualTo(2);
	}

	@Test
	void testUpdate() {
		//given
		Users users = Users.builder()
			.email("test@test.com")
			.password("testpassword")
			.fourteenAgreement(true)
			.adAgreement(true)
			.personalAuthentication(true)
			.gender("Male")
			.phoneNumber("01012345678")
			.rank(Users.Rank.BRONZE)
			.name("testuser")
			.build();
		usersMapper.save(users);

		SalesAccount salesAccount = SalesAccount.builder()
			.users(users)
			.bankName("Test Bank")
			.accountNumber("123-456-7890")
			.accountHolder("Test User")
			.build();
		salesAccountMapper.save(salesAccount);

		//when
		SalesAccount salesAccountUpdated = SalesAccount.builder()
			.id(salesAccount.getId())
			.users(users)
			.bankName("New Test Bank")
			.accountNumber("123-456-7890")
			.accountHolder("Test User")
			.build();
		salesAccountMapper.update(salesAccountUpdated);

		//then
		assertThat(salesAccountMapper.findById(salesAccount.getId())).isEqualTo(salesAccountUpdated);

	}

	@Test
	void testDeleteById() {
		//given
		Users users = Users.builder()
			.email("test@test.com")
			.password("testpassword")
			.fourteenAgreement(true)
			.adAgreement(true)
			.personalAuthentication(true)
			.gender("Male")
			.phoneNumber("01012345678")
			.rank(Users.Rank.BRONZE)
			.name("testuser")
			.build();
		usersMapper.save(users);

		SalesAccount salesAccount = SalesAccount.builder()
			.users(users)
			.bankName("Test Bank")
			.accountNumber("123-456-7890")
			.accountHolder("Test User")
			.build();
		salesAccountMapper.save(salesAccount);

		//when
		salesAccountMapper.deleteById(salesAccount.getId());

		//then
		assertThat(salesAccountMapper.findById(salesAccount.getId())).isNull();
	}
}