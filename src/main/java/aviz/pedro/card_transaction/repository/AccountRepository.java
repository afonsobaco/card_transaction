package aviz.pedro.card_transaction.repository;

import aviz.pedro.card_transaction.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
