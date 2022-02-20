package aviz.pedro.card_transaction.utils;

import aviz.pedro.card_transaction.model.Account;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountTestUtils {

	public Account getAccount() {
		return getAccount(12345678901L, 1L);
	}

	public Account getAccount(Long documentNumber) {
		return Account.builder()
				.documentNumber(documentNumber)
				.id(1L)
				.build();
	}

	public Account getAccount(Long documentNumber, Long account_id) {
		return Account.builder()
				.documentNumber(documentNumber)
				.id(account_id)
				.build();
	}
}
