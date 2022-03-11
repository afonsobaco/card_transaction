package aviz.pedro.card_transaction.service;

import aviz.pedro.card_transaction.exception.AccountNotFoundException;
import aviz.pedro.card_transaction.exception.IllegalOperationTypeException;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.OperationType;
import aviz.pedro.card_transaction.model.Transaction;
import aviz.pedro.card_transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

/**
 * @author Pedro.Aviz
 */
@Service
public class TransactionService implements ITransactionService {

	final TransactionRepository transactionRepository;
	final AccountService accountService;

	public TransactionService(TransactionRepository transactionRepository,
			AccountService accountService) {
		this.transactionRepository = transactionRepository;
		this.accountService = accountService;
	}

	@Override
	public Transaction createTransaction(Long accountId, int operationTypeId, Double amount) {
		OperationType operationType = OperationType.of(operationTypeId);
		if ((operationType.isDecreaseValue() && amount > 0) || (!operationType.isDecreaseValue() && amount < 0)) {
			throw new IllegalOperationTypeException(
					String.format("The operation type %d (%s) does not allow the value %f", operationTypeId,
							operationType.getDescription(), amount));
		}
		Account account = accountService.getAccount(accountId);
		if (account == null) {
			throw new AccountNotFoundException(String.format("There is no account with id: %s", accountId));
		}
		Double newLimit = verifyLimit(account.getLimit(), operationType, amount);
		account.setLimit(newLimit);

		Transaction transaction = Transaction.builder()
				.account(account)
				.operationType(operationType)
				.amount(amount)
				.build();
		Transaction saved = transactionRepository.save(transaction);
		accountService.updateAccount(account);
		return saved;
	}

	private Double verifyLimit(Double limit, OperationType operationType, Double amount) {
		double newLimit = limit + amount;
		if (operationType.isDecreaseValue()) {
			if (newLimit < 0) {
				throw new IllegalArgumentException("The amount exceeds the account limit");
			}
		}
		return newLimit;
	}

}
