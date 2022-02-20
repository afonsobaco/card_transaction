package aviz.pedro.card_transaction.utils;

import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.OperationType;
import aviz.pedro.card_transaction.model.Transaction;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionTestUtils {
	public Transaction getTransaction(Double amount, OperationType operationType, Account account) {
		return Transaction.builder()
				.account(account)
				.amount(amount)
				.operationType(operationType)
				.build();
	}
}
