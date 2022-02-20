package aviz.pedro.card_transaction.service;

import aviz.pedro.card_transaction.model.Account;

public interface IAccountService {
	Account createAccount(Long documentNumber);

	Account getAccount(long accountId);
}
