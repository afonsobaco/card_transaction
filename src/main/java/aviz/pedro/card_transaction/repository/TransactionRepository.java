package aviz.pedro.card_transaction.repository;

import aviz.pedro.card_transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
