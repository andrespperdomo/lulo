package com.lulo.lulobank;


import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lulo.lulobank.constants.ServiceConstant;
import com.lulo.lulobank.enums.ViolationsEnum;
import com.lulo.lulobank.model.Account;
import com.lulo.lulobank.model.AccountRq;
import com.lulo.lulobank.model.AccountTransactionDTO;
import com.lulo.lulobank.model.Transaction;
import com.lulo.lulobank.model.TransactionRq;
import com.lulo.lulobank.util.DateUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ResourceBundle;

import javax.ws.rs.core.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class TransactionTest extends AccountTransactionDTO {
	
	   @Autowired
	   MockMvc mockMvc;
	   
	   ResourceBundle constantes = ResourceBundle.getBundle("application");

	    @Test
	    @Order(1)
	    public void createAccountOk() throws Exception {
	        this.mockMvc.perform(post(constantes.getString(ServiceConstant.PATH_ACCOUNT))
	        .content(asJsonString(new AccountRq(getAccount())))
	        .contentType(MediaType.APPLICATION_JSON)
	        .accept(MediaType.APPLICATION_JSON))
	        .andExpect(status().isCreated())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.violations").isArray());

	    }
	    
	    @Test
	    @Order(2)
	    public void accountAlreadyInit() throws Exception {
			this.mockMvc
					.perform(post(constantes.getString(ServiceConstant.PATH_ACCOUNT)).content(asJsonString(new AccountRq(getAccount())))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.violations[0]")
							.value(ViolationsEnum.ACCOUNT_ALREADY_INIT.getDescripcion()));

	    }
	    
	    @Test
	    @Order(3)
	    public void accountNotInit() throws Exception {
			this.mockMvc
					.perform(post(constantes.getString(ServiceConstant.PATH_ACCOUNT)).content(asJsonString(new AccountRq(getAccountNoActiveCard())))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.violations[0]")
							.value(ViolationsEnum.ACCOUNT_NOT_INIT.getDescripcion()));

	    }
	    
	    @Test
	    @Order(4)
	    public void transactionOk() throws Exception {
			this.mockMvc
					.perform(post(constantes.getString(ServiceConstant.PATH_TRANSACTION)).content(asJsonString(getTransactionOk()))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.violations").isArray());

	    }
	    
	    @Test
	    @Order(5)
	    public void cardNotActive() throws Exception {
			this.mockMvc
					.perform(post(constantes.getString(ServiceConstant.PATH_TRANSACTION)).content(asJsonString(getTranNoActiveCard()))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.violations[0]")
							.value(ViolationsEnum.CARD_NOT_ACTIVE.getDescripcion()));

	    }
	    
	    
	    @Test
	    @Order(6)
	    public void insufficientLimit() throws Exception {
			this.mockMvc
					.perform(post(constantes.getString(ServiceConstant.PATH_TRANSACTION)).content(asJsonString(getTransactionInsufiLimit()))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.violations[0]")
							.value(ViolationsEnum.INSUFFICIENT_LIMIT.getDescripcion()));

	    }
	    
	    @Test
	    @Order(7)
	    public void doubleTransaction() throws Exception {
	    	
	    	this.mockMvc
			.perform(post(constantes.getString(ServiceConstant.PATH_TRANSACTION)).content(asJsonString(getDoubleTransaction()))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.violations").isArray());

			this.mockMvc
					.perform(post(constantes.getString(ServiceConstant.PATH_TRANSACTION)).content(asJsonString(getDoubleTransaction()))
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.violations[0]")
							.value(ViolationsEnum.DOUBLE_TRANSACTION.getDescripcion()));
	    
	    }

	    @Test
	    @Order(8)
	    public void highFrequencySmallIntervall() throws Exception {
	    	transactionOk();
	    	transactionOk();
	    	transactionOk();
	    	this.mockMvc
			.perform(post(constantes.getString(ServiceConstant.PATH_TRANSACTION)).content(asJsonString(getTransactionOk()))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.violations[0]")
					.value(ViolationsEnum.HIGH_FREQUENCY_SMALL_INTERVAL.getDescripcion()));
	    	

	    }
	    
	   
	    
	    
	    
	    
	   
	    
	
	    
	    private static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }

}
