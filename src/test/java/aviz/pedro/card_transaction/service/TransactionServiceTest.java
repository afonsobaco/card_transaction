package aviz.pedro.card_transaction.service;

import aviz.pedro.card_transaction.exception.AccountNotFoundException;
import aviz.pedro.card_transaction.exception.IllegalOperationTypeException;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.OperationType;
import aviz.pedro.card_transaction.model.Transaction;
import aviz.pedro.card_transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

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

	@ParameterizedTest
	@MethodSource("getIllegalArgumentTests")
	void createTransaction_shouldThrowIllegalArgumentException(int operationTypeId, String description, Double amount,
			boolean hasMessage) {
		IllegalOperationTypeException exception = assertThrows(IllegalOperationTypeException.class,
				() -> transactionService.createTransaction(1L, operationTypeId, amount));
		verify(transactionRepository, never()).save(any());
		if (hasMessage) {
			assertEquals(exception.getMessage(), String.format("The operation type %d (%s) does not allow the value %f", operationTypeId,
					description, amount));
		} else {
			assertNull(exception.getMessage());
		}
	}

	private static Stream<Arguments> getIllegalArgumentTests() {
		return Stream.of(
				Arguments.of(OperationType.PURCHASE.getId(), OperationType.PURCHASE.getDescription(), 10.0, true),
				Arguments.of(OperationType.INSTALMENT.getId(), OperationType.INSTALMENT.getDescription(), 10.0, true),
				Arguments.of(OperationType.WITHDRAW.getId(), OperationType.WITHDRAW.getDescription(), 10.0, true),
				Arguments.of(OperationType.PAYMENT.getId(), OperationType.PAYMENT.getDescription(), -10.0, true),
				Arguments.of(100, null, 10.0, false),
				Arguments.of(100, null, -10.0, false)
		);
	}

}