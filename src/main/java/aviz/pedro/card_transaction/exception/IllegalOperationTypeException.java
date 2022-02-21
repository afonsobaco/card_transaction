package aviz.pedro.card_transaction.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalOperationTypeException extends RuntimeException {
	public IllegalOperationTypeException() {
		super();
	}

	public IllegalOperationTypeException(String message) {
		super(message);
	}
}
