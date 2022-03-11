package aviz.pedro.card_transaction.service;

import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {

	private final AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Account createAccount(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public Account getAccount(long accountId) {
		return accountRepository.findById(accountId).orElse(null);
	}

	@Override
	public Account updateAccount(Account account) {
		return accountRepository.save(account);
	}
}
