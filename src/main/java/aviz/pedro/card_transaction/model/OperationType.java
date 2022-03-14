package aviz.pedro.card_transaction.model;

import aviz.pedro.card_transaction.exception.IllegalOperationTypeException;
import lombok.Getter;

import javax.persistence.Id;
import java.util.stream.Stream;

@Getter
public enum OperationType {

	PURCHASE(1, "COMPRA A VISTA", true),
	INSTALMENT(2, "COMPRA PARCELADA", true),
	WITHDRAW(3, "SAQUE", true),
	PAYMENT(4, "PAGAMENTO", false);

	OperationType(int id, String description, boolean decreaseValue) {
		this.id = id;
		this.description = description;
		this.decreaseValue = decreaseValue;
	}

	@Id
	private int id;
	private String description;
	private boolean decreaseValue;

	public static OperationType of(int operationTypeId) {
		return Stream.of(OperationType.values()).filter(ot -> ot.getId() == operationTypeId).findFirst()
				.orElseThrow(() -> new IllegalOperationTypeException(String.format("Illegal OperationType %d ", operationTypeId)));
	}
}
