package com.lulo.lulobank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lulo.lulobank.constants.ServiceConstant;
import com.lulo.lulobank.enums.ViolationsEnum;
import com.lulo.lulobank.exception.AccountException;
import com.lulo.lulobank.model.Account;
import com.lulo.lulobank.model.AccountRq;
import com.lulo.lulobank.model.AccountRs;
import com.lulo.lulobank.model.TransactionRq;
import com.lulo.lulobank.service.AccountTransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Account Rest Controller", description = "REST API for User")
@RestController
@RequestMapping(path="${account}")
public class AccountController {

	@Autowired
	AccountTransactionService accountService;
	

	 @ApiOperation(value = "Create account", notes="provider create account"
	 		, response = Iterable.class, tags = "createAccount")
		@ApiResponses(value = { 
				@ApiResponse(code = 201, message = "account-already-initialized|account-not-initialized", response = AccountRs.class),
				@ApiResponse(code = 500, message = "", response = AccountException.class)})
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountRs> createAccount(@RequestBody AccountRq request) {
		AccountRs rs = null;
		try {
			rs=accountService.createTransaction(request);
		} catch (AccountException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}
	 

}

