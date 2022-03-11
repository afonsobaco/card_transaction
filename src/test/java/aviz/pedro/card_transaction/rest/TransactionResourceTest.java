package aviz.pedro.card_transaction.rest;

import aviz.pedro.card_transaction.dto.TransactionDto;
import aviz.pedro.card_transaction.model.Account;
import aviz.pedro.card_transaction.model.OperationType;
import aviz.pedro.card_transaction.model.Transaction;
import aviz.pedro.card_transaction.service.TransactionService;
import aviz.pedro.card_transaction.service.TransactionService;
import aviz.pedro.card_transaction.utils.TransactionTestUtils;
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
		Transaction transaction = TransactionTestUtils.getTransactionDefault();
		TransactionDto transactionDto = TransactionTestUtils.getTransactionDtoDefault();

		when(transactionService.createTransaction(transactionDto.getAccountId(), transactionDto.getOperationTypeId(), transactionDto.getAmount())).thenReturn(transaction);

		mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
						.content(getObjectAsString(transactionDto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.account_id").value(transaction.getAccount().getId()))
				.andExpect(jsonPath("$.operation_type_id").value(transaction.getOperationType().getId()))
				.andExpect(jsonPath("$.amount").value(transaction.getAmount()))
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