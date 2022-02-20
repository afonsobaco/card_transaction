package aviz.pedro.card_transaction.repository;

import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.OperationType;
import aviz.pedro.card_transaction.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Sql({"/account.sql"})
class TransactionRepositoryTest {

	@Autowired
	TransactionRepository transactionRepository;

	@Test
	void save_shouldAutoGenerateAccountId() {
		Long documentNumber = 12345678901L;
		Long account_id = 2L;
		Account account = Account.builder().documentNumber(documentNumber).id(account_id).build();

		Double amount = 10.10;
		OperationType operationType = OperationType.PURCHASE;
		Transaction transaction = Transaction.builder().account(account).amount(amount).operationType(operationType).build();
		Transaction result = transactionRepository.save(transaction);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(account.getId(), result.getAccount().getId());
		assertEquals(account.getDocumentNumber(), result.getAccount().getDocumentNumber());
		assertEquals(transaction.getAmount(), result.getAmount());
		assertEquals(transaction.getOperationType(), result.getOperationType());
		assertNotNull(transaction.getEventDate());
	}

}