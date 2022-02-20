package aviz.pedro.card_transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TransactionDto {

	@JsonProperty("account_id")
	private Long accountId;

	@JsonProperty("operation_type_id")
	private int operationTypeId;

	@JsonProperty("amount")
	private Double amount;
}
