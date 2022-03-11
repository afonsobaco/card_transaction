package aviz.pedro.card_transaction.service;

import aviz.pedro.card_transaction.dto.AccountDto;
import aviz.pedro.card_transaction.model.Account;

public interface IAccountService {
	Account createAccount(Account dto);

	Account getAccount(long accountId);

	Account updateAccount(Account account);
}
