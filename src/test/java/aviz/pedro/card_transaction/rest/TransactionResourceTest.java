package aviz.pedro.card_transaction.rest;

import aviz.pedro.card_transaction.dto.TransactionDto;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.OperationType;
import aviz.pedro.card_transaction.model.Transaction;
import aviz.pedro.card_transaction.service.TransactionService;
import aviz.pedro.card_transaction.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionResource.class)
class TransactionResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionService transactionService;

	@Test
	void post_shouldReturn201() throws Exception {
		long documentNumber = 32132132149L;
		long accountId = 11111L;
		long transactionId = 987654321L;
		OperationType operationType = OperationType.PAYMENT;
		Double amount = 50.05;
		LocalDateTime eventDate = LocalDateTime.now();
		Account account = getAccount(documentNumber, accountId);

		Transaction transaction = getTransaction(transactionId, operationType, eventDate, amount, account);
		TransactionDto transactionDto = getTransactionDto(operationType, amount, account);

		when(transactionService.createTransaction(accountId, operationType.getId(), amount)).thenReturn(transaction);

		mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
						.content(getObjectAsString(transactionDto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.account_id").value(accountId))
				.andExpect(jsonPath("$.operation_type_id").value(operationType.getId()))
				.andExpect(jsonPath("$.amount").value(amount))
				.andReturn();
	}

	private Account getAccount(long documentNumber, long accountId) {
		return Account.builder()
				.id(accountId)
				.documentNumber(documentNumber)
				.build();
	}

	private Transaction getTransaction(long transactionId, OperationType operationType, LocalDateTime eventDate,
			Double amount, Account account) {
		return Transaction.builder()
				.operationType(operationType)
				.account(account)
				.eventDate(eventDate)
				.amount(amount)
				.id(transactionId)
				.build();
	}

	private TransactionDto getTransactionDto(OperationType operationType, Double amount, Account account) {
		return TransactionDto.builder()
				.operationTypeId(operationType.getId())
				.accountId(account.getId())
				.amount(amount)
				.build();
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