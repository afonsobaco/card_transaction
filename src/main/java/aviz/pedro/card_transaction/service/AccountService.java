package aviz.pedro.card_transaction.service;

import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountService implements IAccountService {

	private final AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Account createAccount(Account account) {
		if(account.getId() != null && account.getId() > 0){
			return updateAccount(account);
		}
		Account createdAccount = accountRepository.save(account);
		log.info("Account with id {} created for document number {} and limit {}", createdAccount.getId(),
				createdAccount.getDocumentNumber(), createdAccount.getLimit());
		return createdAccount;
	}

	@Override
	public Account getAccount(long accountId) {
		Account account = accountRepository.findById(accountId).orElse(null);
		if (account != null) {
			log.info("Account with id {} Found!", account.getId());
		}
		else {
			log.error("Account with id {} not found!", accountId);
		}
		return account;
	}

	@Override
	public Account updateAccount(Account account) {
		Account updatedAccount = accountRepository.save(account);
		log.info("Account with id {} updated for document number {} and limit {}", updatedAccount.getId(),
				updatedAccount.getDocumentNumber(), updatedAccount.getLimit());
		return updatedAccount;
	}
}
