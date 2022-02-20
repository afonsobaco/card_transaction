package aviz.pedro.card_transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CardTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardTransactionApplication.class, args);
	}

}
