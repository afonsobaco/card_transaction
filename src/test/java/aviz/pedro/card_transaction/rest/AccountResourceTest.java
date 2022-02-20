package aviz.pedro.card_transaction.rest;

import aviz.pedro.card_transaction.dto.AccountDto;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountResource.class)
class AccountResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	@Test
	void post_shouldReturn201() throws Exception {
		long documentNumber = 123456789L;
		long accountId = 1L;
		Account account = Account.builder().documentNumber(documentNumber).id(accountId).build();
		when(accountService.createAccount(any())).thenReturn(account);
		AccountDto accountDto = AccountDto.builder().documentNumber(documentNumber).build();
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
						.content(getObjectAsString(accountDto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.document_number").value(documentNumber))
				.andExpect(jsonPath("$.account_id").value(accountId))
				.andReturn();
	}

	@Test
	void get_shouldReturnAccount() throws Exception {
		long documentNumber = 123456789L;
		long accountId = 1L;
		Account account = Account.builder().documentNumber(documentNumber).id(accountId).build();
		when(accountService.getAccount(accountId)).thenReturn(account);
		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/{accountId}",accountId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.document_number").value(documentNumber))
				.andExpect(jsonPath("$.account_id").value(accountId))
				.andReturn();
	}

	private String getObjectAsString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Error while mapping object to String");
		}
	}
}