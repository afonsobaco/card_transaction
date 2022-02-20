package aviz.pedro.card_transaction.service;

import aviz.pedro.card_transaction.exception.AccountNotFoundException;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.OperationType;
import aviz.pedro.card_transaction.model.Transaction;
import aviz.pedro.card_transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

	@Mock
	TransactionRepository transactionRepository;
	@Mock
	AccountService accountService;

	@InjectMocks
	TransactionService transactionService;

	@Test
	void createTransaction_shouldCreateTransaction() {
		Long accountId = 1L;
		OperationType operationType = OperationType.PAYMENT;
		Double amount = 0.0;

		Account account = Account.builder()
				.id(accountId)
				.documentNumber(123L)
				.build();
		when(accountService.getAccount(accountId)).thenReturn(account);
		when(transactionRepository.save(any())).thenAnswer(x -> x.getArgument(0));

		Transaction transaction = transactionService.createTransaction(accountId, operationType.getId(), amount);
		assertNotNull(transaction);
		assertEquals(accountId, transaction.getAccount().getId());
		assertEquals(operationType, transaction.getOperationType());
		assertEquals(amount, transaction.getAmount());
	}

	@Test
	void createTransaction_shouldThrowAccountNotFoundException() {
		Long accountId = 1L;
		int operationTypeId = 4;
		Double amount = 0.0;

		when(accountService.getAccount(accountId)).thenReturn(null);

		AccountNotFoundException exception = assertThrows(AccountNotFoundException.class,
				() -> transactionService.createTransaction(accountId, operationTypeId, amount));

		verify(transactionRepository, never()).save(any());
		assertEquals(String.format("There is no account with id: %s", accountId), exception.getMessage());
	}
}