package aviz.pedro.card_transaction.utils;

import aviz.pedro.card_transaction.dto.TransactionDto;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.OperationType;
import aviz.pedro.card_transaction.model.Transaction;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class TransactionTestUtils {

	public static final double AMOUNT = -10.0;
	public static final int OPERATION_TYPE_ID = OperationType.PURCHASE.getId();
	public static final long TRANSACTION_ID = 1L;
	public static final LocalDateTime EVENT_DATE = LocalDateTime.now();

	public Transaction getTransactionDefault() {
		Account account = AccountTestUtils.getAccountDefault();
		return Transaction.builder()
				.id(TRANSACTION_ID)
				.account(account)
				.operationType(OperationType.of(OPERATION_TYPE_ID))
				.amount(AMOUNT)
				.eventDate(EVENT_DATE)
				.build();
	}

	public TransactionDto getTransactionDtoDefault() {
		return TransactionDto.builder()
				.accountId(AccountTestUtils.ACCOUNT_ID)
				.operationTypeId(OPERATION_TYPE_ID)
				.amount(AMOUNT)
				.build();
	}
}
