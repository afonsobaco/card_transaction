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

		Transaction transaction = TransactionTestUtils.getTransactionDefault();
		Transaction result = transactionRepository.save(transaction);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(transaction.getAccount().getId(), result.getAccount().getId());
		assertEquals(transaction.getAccount().getDocumentNumber(), result.getAccount().getDocumentNumber());
		assertEquals(transaction.getAmount(), result.getAmount());
		assertEquals(transaction.getOperationType(), result.getOperationType());
		assertNotNull(transaction.getEventDate());
	}

	@Test
	void save_shouldThrowExceptionWhenAmountIsNull() {
		Transaction transaction = TransactionTestUtils.getTransactionDefault();
		transaction.setAmount(null);
		assertThrows(DataIntegrityViolationException.class, () -> transactionRepository.save(transaction));
	}

	@Test
	void save_shouldThrowExceptionWhenOperationTypeIsNull() {
		Transaction transaction = TransactionTestUtils.getTransactionDefault();
		transaction.setOperationType(null);
		assertThrows(DataIntegrityViolationException.class, () -> transactionRepository.save(transaction));
	}

	@Test
	void save_shouldThrowExceptionWhenAccountIsNull() {
		Transaction transaction = TransactionTestUtils.getTransactionDefault();
		transaction.setAccount(null);
		assertThrows(DataIntegrityViolationException.class, () -> transactionRepository.save(transaction));
	}

}