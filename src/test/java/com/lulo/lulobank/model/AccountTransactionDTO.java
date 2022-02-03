package com.lulo.lulobank.model;

import com.lulo.lulobank.util.DateUtil;

public class AccountTransactionDTO {
	
	 public Account getAccount() {
	    	Account acc=new Account();
	    	acc.setId(1L);
	    	acc.setActiveCard(true);
	    	acc.setAvailableLimit(50);
	    	return acc;
	    }
	    
	    public Account getAccountNoActiveCard() {
	    	Account acc=new Account();
	    	acc.setId(4L);
	    	acc.setActiveCard(false);
	    	acc.setAvailableLimit(50);
	    	return acc;
	    }
	    
	    public TransactionRq getTransactionOk() {
	    	TransactionRq tranRq=new TransactionRq();
	    	Transaction tran=new Transaction();
	    	tran.setId(1L);
	    	tran.setAmount(2);
	    	tran.setMerchant("AWS"+Math.random()*10+1);
	    	
	    	tran.setTime(DateUtil.sysdateString());
	    	tranRq.setTransaction(tran);
	    	return tranRq;
	    }
	    
	    public TransactionRq getTranNoActiveCard() {
	    	getAccountNoActiveCard();
	    	TransactionRq tranRq=new TransactionRq();
	    	Transaction tran=new Transaction();
	    	tran.setId(4L);
	    	tran.setAmount(2);
	    	tran.setMerchant("AWS");
	    	
	    	tran.setTime(DateUtil.sysdateString());
	    	tranRq.setTransaction(tran);
	    	return tranRq;
	    }
	    
	    public TransactionRq getTransactionInsufiLimit() {
	    	TransactionRq tranRq=new TransactionRq();
	    	Transaction tran=new Transaction();
	    	tran.setId(1L);
	    	tran.setAmount(60);
	    	tran.setMerchant("AWS");
	    	
	    	tran.setTime(DateUtil.sysdateString());
	    	tranRq.setTransaction(tran);
	    	return tranRq;
	    }
	    
	    public TransactionRq getDoubleTransaction() {
	    	TransactionRq tranRq=new TransactionRq();
	    	Transaction tran=new Transaction();
	    	tran.setId(1L);
	    	tran.setAmount(2);
	    	tran.setMerchant("AWS");
	    	
	    	tran.setTime(DateUtil.sysdateString());
	    	tranRq.setTransaction(tran);
	    	return tranRq;
	    }

}
