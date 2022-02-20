package aviz.pedro.card_transaction.model.converter;

import aviz.pedro.card_transaction.model.OperationType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OperationTypeConverter implements AttributeConverter<OperationType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(OperationType operationType) {
		if (operationType == null) {
			return null;
		}
		return operationType.getId();
	}

	@Override
	public OperationType convertToEntityAttribute(Integer id) {
		if (id == null) {
			return null;
		}
		return Stream.of(OperationType.values())
				.filter(c -> c.getId() == id)
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}