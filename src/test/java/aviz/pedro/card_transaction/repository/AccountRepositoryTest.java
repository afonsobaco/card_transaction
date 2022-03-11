package aviz.pedro.card_transaction.repository;

import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.utils.AccountTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class AccountRepositoryTest {
	@Autowired
	AccountRepository accountRepository;

	@Test
	void save_shouldAutoGenerateAccountId()
	{
		Account account = AccountTestUtils.getAccountDefault();
		account.setId(null);
		Account result = accountRepository.save(account);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(account.getDocumentNumber(), result.getDocumentNumber());
	}

	@Test
	void save_shouldThrowExceptionWhenDocumentNumberIsNull()
	{
		Account account = Account.builder().documentNumber(null).build();
		assertThrows(DataIntegrityViolationException.class, () ->  accountRepository.save(account));
	}

	@Test
	void save_shouldThrowExceptionWhenAccountLimitIsNull()
	{
		Account account = Account.builder().limit(null).build();
		assertThrows(DataIntegrityViolationException.class, () ->  accountRepository.save(account));
	}

}