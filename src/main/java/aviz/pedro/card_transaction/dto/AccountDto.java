package aviz.pedro.card_transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AccountDto {

	@JsonProperty("document_number")
	@NotNull(message = "documentNumber should not be null")
	private Long documentNumber;

	@JsonProperty("limit")
	@NotNull(message = "limit should not be null")
	@Min(message = "limit should not be negative", value = 0)
	private Double limit;

	@JsonProperty("account_id")
	private Long accountId;
}
