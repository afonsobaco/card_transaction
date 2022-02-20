package aviz.pedro.card_transaction.model;

import lombok.Getter;

import javax.persistence.Id;
import java.util.stream.Stream;

@Getter
public enum OperationType {

	PURCHASE(1, "COMPRA A VISTA"),
	INSTALMENT(2, "COMPRA PARCELADA"),
	WITHDRAW(3, "SAQUE"),
	PAYMENT(4, "PAGAMENTO");

	OperationType(int id, String description) {
		this.id = id;
		this.description = description;
	}

	@Id
	private int id;
	private String description;

	public static OperationType of(int operationTypeId) {
		return Stream.of(OperationType.values()).filter(ot -> ot.getId() == operationTypeId).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
