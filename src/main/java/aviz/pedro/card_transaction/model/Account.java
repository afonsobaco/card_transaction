package aviz.pedro.card_transaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "document_number", nullable = false)
	private Long documentNumber;

	@Column(name = "account_limit", nullable = false )
	private Double limit;
}
