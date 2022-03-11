package aviz.pedro.card_transaction.rest;

import aviz.pedro.card_transaction.dto.AccountDto;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.service.AccountService;
import aviz.pedro.card_transaction.utils.AccountTestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
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

		when(accountService.createAccount(any())).thenReturn(AccountTestUtils.getAccountDefault());

		AccountDto accountDtoDefault = AccountTestUtils.getAccountDtoDefault();
		accountDtoDefault.setAccountId(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
						.content(getObjectAsString(accountDtoDefault))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.document_number").value(AccountTestUtils.DOCUMENT_NUMBER))
				.andExpect(jsonPath("$.account_id").value(AccountTestUtils.ACCOUNT_ID))
				.andReturn();
	}

	@Test
	void post_shouldThrowExceptionWhenDocumentNumberIsNull() throws Exception {
		AccountDto accountDto = AccountTestUtils.getAccountDtoDefault();
		accountDto.setDocumentNumber(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
						.content(getObjectAsString(accountDto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.errors[0]").exists())
				.andExpect(jsonPath("$.errors[0]").value("documentNumber: documentNumber should not be null"))
				.andExpect(jsonPath("$.message").value(containsString("Validation failed for argument")))
				.andReturn();
	}

	@Test
	void post_shouldThrowExceptionWhenLimitIsNull() throws Exception {
		AccountDto accountDto = AccountTestUtils.getAccountDtoDefault();
		accountDto.setLimit(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
						.content(getObjectAsString(accountDto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.errors[0]").exists())
				.andExpect(jsonPath("$.errors[0]").value("limit: limit should not be null"))
				.andExpect(jsonPath("$.message").value(containsString("Validation failed for argument")))
				.andReturn();
	}

	@Test
	void post_shouldThrowExceptionWhenLimitIsNegative() throws Exception {
		AccountDto accountDto = AccountTestUtils.getAccountDtoDefault();
		accountDto.setLimit(-100.0);

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
						.content(getObjectAsString(accountDto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.errors[0]").exists())
				.andExpect(jsonPath("$.errors[0]").value("limit: limit should not be negative"))
				.andExpect(jsonPath("$.message").value(containsString("Validation failed for argument")))
				.andReturn();
	}

	@Test
	void get_shouldReturnAccount() throws Exception {
		Account account = AccountTestUtils.getAccountDefault();
		when(accountService.getAccount(AccountTestUtils.ACCOUNT_ID)).thenReturn(account);

		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/{accountId}", AccountTestUtils.ACCOUNT_ID)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.document_number").value(AccountTestUtils.DOCUMENT_NUMBER))
				.andExpect(jsonPath("$.account_id").value(AccountTestUtils.ACCOUNT_ID))
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