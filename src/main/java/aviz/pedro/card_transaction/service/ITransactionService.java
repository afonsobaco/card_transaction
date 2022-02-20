package aviz.pedro.card_transaction.service;

import aviz.pedro.card_transaction.model.Transaction;

/**
 * @author Pedro.Aviz
 */
public interface ITransactionService {

	Transaction createTransaction(Long accountId, int operationTypeId, Double amount);
}
