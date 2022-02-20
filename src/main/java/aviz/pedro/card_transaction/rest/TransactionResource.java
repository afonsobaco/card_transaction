package aviz.pedro.card_transaction.rest;

import aviz.pedro.card_transaction.dto.AccountDto;
import aviz.pedro.card_transaction.dto.TransactionDto;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.Transaction;
import aviz.pedro.card_transaction.service.ITransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionResource {

	final ITransactionService transactionService;

	public TransactionResource(ITransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping
	public ResponseEntity<TransactionDto> post(@RequestBody TransactionDto transactionDto) {
		Transaction transaction = transactionService.createTransaction(transactionDto.getAccountId(), transactionDto.getOperationTypeId(), transactionDto.getAmount());
		return ResponseEntity.status(HttpStatus.CREATED).body(parseModelToDto(transaction));
	}

	private TransactionDto parseModelToDto(Transaction transaction) {
		return new ModelMapper().map(transaction, TransactionDto.class);
	}
}
