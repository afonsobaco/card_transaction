package aviz.pedro.card_transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AccountDto {

	@JsonProperty("document_number")
	private Long documentNumber;

	@JsonProperty("account_id")
	private Long accountId;
}
