package aviz.pedro.card_transaction.service;

import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.repository.AccountRepository;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	AccountRepository accountRepository;

	@InjectMocks
	AccountService accountService;

	@Test
	void createAccount_shouldCreateAndReturnAccount() {
		long documentNumber = 123l;
		when(accountRepository.save(any())).thenAnswer(x -> x.getArgument(0));
		Account account = accountService.createAccount(documentNumber);
		assertNotNull(account);
		assertEquals(documentNumber, account.getDocumentNumber());
	}

	@Test
	void getAccount_shouldReturnAccount() {
		long accountId = 1l;
		long documentNumber = 123l;
		Account account = Account.builder()
				.id(accountId)
				.documentNumber(documentNumber)
				.build();

		when(accountRepository.findById(accountId)).thenReturn(Optional.ofNullable(account));

		Account result = accountService.getAccount(accountId);
		assertNotNull(result);
		assertEquals(accountId, result.getId());
	}

	@Test
	void getAccount_shouldReturnNull() {
		long accountId = 1l;
		when(accountRepository.findById(accountId)).thenReturn(Optional.ofNullable(null));
		Account result = accountService.getAccount(accountId);
		assertNull(result);
	}

}