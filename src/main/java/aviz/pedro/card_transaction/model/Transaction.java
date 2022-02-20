package aviz.pedro.card_transaction.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@Column(name = "operation_type_id", nullable = false)
	private OperationType operationType;

	@Column(name = "amount", nullable = false)
	private Double amount;

	@CreatedDate
	@Column(name = "event_date", columnDefinition = "TIMESTAMP", nullable = false)
	private LocalDateTime eventDate;

}
