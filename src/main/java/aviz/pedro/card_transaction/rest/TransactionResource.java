package aviz.pedro.card_transaction.rest;

import aviz.pedro.card_transaction.dto.AccountDto;
import aviz.pedro.card_transaction.dto.TransactionDto;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.Transaction;
import aviz.pedro.card_transaction.service.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionResource {

	final ITransactionService transactionService;

	public TransactionResource(ITransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping
	@Operation(summary = "Create transaction",
			tags = {"transaction"},
			description = "Create transaction with the given information",
			responses = {
					@ApiResponse(responseCode = "201", description = "Created successfully", content = @Content(schema = @Schema(implementation = TransactionDto.class))),
					@ApiResponse(responseCode = "400", description = "Invalid information supplied"),
					@ApiResponse(responseCode = "404", description = "Account supplied not found")
			})
	public ResponseEntity<TransactionDto> post(@RequestBody TransactionDto transactionDto) {
		Transaction transaction = transactionService.createTransaction(transactionDto.getAccountId(), transactionDto.getOperationTypeId(), transactionDto.getAmount());
		return ResponseEntity.status(HttpStatus.CREATED).body(parseModelToDto(transaction));
	}

	private TransactionDto parseModelToDto(Transaction transaction) {
		return new ModelMapper().map(transaction, TransactionDto.class);
	}

}
