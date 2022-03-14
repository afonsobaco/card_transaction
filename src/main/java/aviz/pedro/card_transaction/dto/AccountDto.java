package aviz.pedro.card_transaction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(NON_NULL)
public class AccountDto {

	@JsonProperty("document_number")
	@NotNull(message = "documentNumber should not be null")
	private Long documentNumber;

	@JsonProperty("limit")
	@NotNull(message = "limit should not be null")
	@Min(message = "limit should not be negative", value = 0)
	private Double limit;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty("account_id")
	private Long accountId;
}
