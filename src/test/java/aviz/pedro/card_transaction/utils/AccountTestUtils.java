package aviz.pedro.card_transaction.utils;

import aviz.pedro.card_transaction.dto.AccountDto;
import aviz.pedro.card_transaction.model.Account;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountTestUtils {

	public static final long DOCUMENT_NUMBER = 191L;
	public static final double LIMIT = 100.0;
	public static final long ACCOUNT_ID = 1L;

	public Account getAccountDefault() {
		long documentNumber = DOCUMENT_NUMBER;
		Double limit = LIMIT;
		long accountId = ACCOUNT_ID;
		return Account.builder().documentNumber(documentNumber).limit(limit).id(accountId).build();
	}

	public AccountDto getAccountDtoDefault() {
		long documentNumber = DOCUMENT_NUMBER;
		Double limit = LIMIT;
		long accountId = ACCOUNT_ID;
		return AccountDto.builder().documentNumber(documentNumber).limit(limit).accountId(accountId).build();
	}
}
