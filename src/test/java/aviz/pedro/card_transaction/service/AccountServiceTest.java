package aviz.pedro.card_transaction.service;

import aviz.pedro.card_transaction.dto.AccountDto;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.repository.AccountRepository;
import aviz.pedro.card_transaction.utils.AccountTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	AccountRepository accountRepository;

	@InjectMocks
	AccountService accountService;

	@Test
	void createAccount_shouldCreateAndReturnAccount() {
		when(accountRepository.save(any())).thenAnswer(x -> x.getArgument(0));
		Account accountDefault = AccountTestUtils.getAccountDefault();

		Account account = accountService.createAccount(accountDefault);
		assertNotNull(account);
		assertEquals(accountDefault.getDocumentNumber(), account.getDocumentNumber());
	}

	@Test
	void createAccount_shouldCreateAndReturnAccountWhenLimitIsZero() {
		when(accountRepository.save(any())).thenAnswer(x -> x.getArgument(0));
		Account accountDefault = AccountTestUtils.getAccountDefault();
		accountDefault.setLimit(0.0);

		Account account = accountService.createAccount(accountDefault);
		assertNotNull(account);
		assertEquals(accountDefault.getDocumentNumber(), account.getDocumentNumber());
		assertEquals(accountDefault.getLimit(), account.getLimit());
	}

	@Test
	void getAccount_shouldReturnAccount() {
		Account accountDefault =  AccountTestUtils.getAccountDefault();
		when(accountRepository.findById(accountDefault.getId())).thenReturn(Optional.ofNullable(accountDefault));

		Account result = accountService.getAccount(accountDefault.getId());
		assertNotNull(result);
		assertEquals(accountDefault.getId(), result.getId());
		assertEquals(accountDefault.getDocumentNumber(), result.getDocumentNumber());
		assertEquals(accountDefault.getLimit(), result.getLimit());
	}

	@Test
	void getAccount_shouldReturnNull() {
		long accountId = -9999l;
		when(accountRepository.findById(accountId)).thenReturn(Optional.ofNullable(null));
		Account result = accountService.getAccount(accountId);
		assertNull(result);
	}

}