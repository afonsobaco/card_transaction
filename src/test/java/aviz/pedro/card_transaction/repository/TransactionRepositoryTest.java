package aviz.pedro.card_transaction.repository;

import aviz.pedro.card_transaction.CardTransactionApplication;
import aviz.pedro.card_transaction.config.JpaAuditingConfiguration;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.OperationType;
import aviz.pedro.card_transaction.model.Transaction;
import aviz.pedro.card_transaction.utils.AccountTestUtils;
import aviz.pedro.card_transaction.utils.TransactionTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Sql({ "/account.sql" })
@ContextConfiguration(classes = { CardTransactionApplication.class, JpaAuditingConfiguration.class })
class TransactionRepositoryTest {

	@Autowired
	TransactionRepository transactionRepository;

	@Test
	void save_shouldSaveSuccessfully() {
		Double amount = 10.10;
		OperationType operationType = OperationType.PURCHASE;
		Account account = AccountTestUtils.getAccount();

		Transaction transaction = TransactionTestUtils.getTransaction(amount, operationType, account);
		Transaction result = transactionRepository.save(transaction);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(account.getId(), result.getAccount().getId());
		assertEquals(account.getDocumentNumber(), result.getAccount().getDocumentNumber());
		assertEquals(transaction.getAmount(), result.getAmount());
		assertEquals(transaction.getOperationType(), result.getOperationType());
		assertNotNull(transaction.getEventDate());
	}

	@Test
	void save_shouldThrowExceptionWhenAmountIsNull() {
		Transaction transaction = TransactionTestUtils.getTransaction(null, OperationType.PURCHASE,
				AccountTestUtils.getAccount());
		assertThrows(DataIntegrityViolationException.class, () -> transactionRepository.save(transaction));
	}

	@Test
	void save_shouldThrowExceptionWhenOperationTypeIsNull() {
		Transaction transaction = TransactionTestUtils.getTransaction(10.0, null, AccountTestUtils.getAccount());
		assertThrows(DataIntegrityViolationException.class, () -> transactionRepository.save(transaction));
	}

	@Test
	void save_shouldThrowExceptionWhenAccountIsNull() {
		Transaction transaction = TransactionTestUtils.getTransaction(10.0, OperationType.PURCHASE, null);
		assertThrows(DataIntegrityViolationException.class, () -> transactionRepository.save(transaction));
	}

}