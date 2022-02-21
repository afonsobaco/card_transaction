package aviz.pedro.card_transaction;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(info = @Info(title = "Card Transaction API", version = "0.1", description = "Card Transactions"))
public class CardTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardTransactionApplication.class, args);
	}

}
