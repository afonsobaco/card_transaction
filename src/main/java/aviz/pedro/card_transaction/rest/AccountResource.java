package aviz.pedro.card_transaction.rest;

import aviz.pedro.card_transaction.dto.AccountDto;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountResource {

	final IAccountService accountService;

	public AccountResource(IAccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	@Operation(summary = "Create account",
			tags = {"account"},
			description = "Create account with the given document number",
			responses = {
					@ApiResponse(responseCode = "201", description = "Created successfully", content = @Content(schema = @Schema(implementation = Account.class))),
					@ApiResponse(responseCode = "400", description = "Invalid document number supplied")
			})
	public ResponseEntity<AccountDto> post(@RequestBody AccountDto accountDto) {
		Account account = accountService.createAccount(accountDto.getDocumentNumber());
		return ResponseEntity.status(HttpStatus.CREATED).body(parseModelToDto(account));
	}

	@GetMapping("/{accountId}")
	@Operation(summary = "Get account",
			tags = {"account"},
			description = "Get account with the given id",
			responses = {
					@ApiResponse(description = "The account", content = @Content(schema = @Schema(implementation = Account.class))),
					@ApiResponse(responseCode = "400", description = "Invalid id supplied"),
					@ApiResponse(responseCode = "404", description = "Account not found")
			})
	public ResponseEntity<AccountDto> get(@PathVariable Long accountId) {
		Account account = accountService.getAccount(accountId);
		if (account == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(parseModelToDto(account));
	}

	private AccountDto parseModelToDto(Account account) {
		return new ModelMapper().map(account, AccountDto.class);
	}

}
