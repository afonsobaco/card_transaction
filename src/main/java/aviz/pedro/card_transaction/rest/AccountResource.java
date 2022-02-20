package aviz.pedro.card_transaction.rest;

import aviz.pedro.card_transaction.dto.AccountDto;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountResource {

	final IAccountService accountService;

	public AccountResource(IAccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<AccountDto> post(@RequestBody AccountDto accountDto) {
		Account account = accountService.createAccount(accountDto.getDocumentNumber());
		return ResponseEntity.status(HttpStatus.CREATED).body(parseModelToDto(account));
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<AccountDto> get(@PathVariable Long accountId) {
		Account account = accountService.getAccount(accountId);
		return ResponseEntity.ok(parseModelToDto(account));
	}

	private AccountDto parseModelToDto(Account account) {
		return new ModelMapper().map(account, AccountDto.class);
	}

}
