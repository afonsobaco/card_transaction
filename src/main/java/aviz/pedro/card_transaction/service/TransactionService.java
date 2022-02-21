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

		Transaction transaction = Transaction.builder()
				.account(account)
				.operationType(operationType)
				.amount(amount)
				.build();
		return transactionRepository.save(transaction);
	}

}
