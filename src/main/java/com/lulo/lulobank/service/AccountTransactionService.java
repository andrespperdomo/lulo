package com.lulo.lulobank.service;

import com.lulo.lulobank.exception.AccountException;

import com.lulo.lulobank.model.AccountRq;
import com.lulo.lulobank.model.AccountRs;
import com.lulo.lulobank.model.Transaction;
import com.lulo.lulobank.model.TransactionRq;

public interface AccountTransactionService {

	public AccountRs createTransaction(AccountRq request) throws AccountException ;
	
	public AccountRs updateTransaction(TransactionRq request) throws AccountException;
}
