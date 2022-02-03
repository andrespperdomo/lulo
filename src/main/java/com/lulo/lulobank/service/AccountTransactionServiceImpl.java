package com.lulo.lulobank.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lulo.lulobank.enums.ViolationsEnum;
import com.lulo.lulobank.exception.AccountException;
import com.lulo.lulobank.model.Account;
import com.lulo.lulobank.model.AccountRq;
import com.lulo.lulobank.model.AccountRs;
import com.lulo.lulobank.model.Transaction;
import com.lulo.lulobank.model.TransactionRq;
import com.lulo.lulobank.util.DateUtil;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService{
	
	private static final Logger logger = LoggerFactory.getLogger(AccountTransactionServiceImpl.class);
	

	List<AccountRs> listAccount=new ArrayList<>();
	List<Transaction> listTransaction=new ArrayList<>();
	
	/**
	 * createTransaction
	 */
	@Override
	public AccountRs createTransaction(AccountRq rq) throws AccountException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Account acc = new Account();
		AccountRs rs = new AccountRs();

		Account accRq = rq.getAccount();
		
		if(accRq.getAvailableLimit()<=0) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "(Available limit) No permite valores negativas o menor a 1");
		}

		if (accRq != null) {
			acc.setId(accRq.getId());
			acc.setActiveCard(accRq.isActiveCard());
			acc.setAvailableLimit(accRq.getAvailableLimit());
		}
		
		if (!accRq.isActiveCard()) {
			rs.getViolations().add(ViolationsEnum.ACCOUNT_NOT_INIT.getDescripcion());
			listAccount.add(rs);
		} else {
			if (!validateId(accRq.getId())) {
				listAccount.add(rs);
			} else {
				rs.getViolations().add(ViolationsEnum.ACCOUNT_ALREADY_INIT.getDescripcion());
				// listAccount.add(rs);
			}
		}

		rs.setAccount(accRq);
		return rs;
	}
	/**
	 * updateTransaction
	 */
	@Override
	public AccountRs updateTransaction(TransactionRq rq) throws AccountException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		AccountRs accountRs = new AccountRs();
		accountRs.setAccount(new Account());

		Transaction transaction = rq.getTransaction();
		Optional<AccountRs> accRs = getAccountId(transaction.getId());
	
	    accountRs.getViolations().add(ViolationsEnum.ACCOUNT_NOT_INIT.getDescripcion());
	
		if(accRs.isPresent()){
			accountRs.setViolations(new ArrayList<>());
			Account acc = accRs.get().getAccount();

			if (!acc.isActiveCard()) {
				accountRs.getViolations().add(ViolationsEnum.CARD_NOT_ACTIVE.getDescripcion());
			}

			Long amountTran = amountLimitAccountTransaction(transaction.getId());
			Long  limitTrans= amountTran+transaction.getAmount();
			if (limitTrans > acc.getAvailableLimit()) {
				accountRs.getViolations().add(ViolationsEnum.INSUFFICIENT_LIMIT.getDescripcion());
			} 
			else if (countTransactionInterval2(transaction.getMerchant()) > 3) {
				accountRs.getViolations().add(ViolationsEnum.HIGH_FREQUENCY_SMALL_INTERVAL.getDescripcion());
			} 
			else if (sameAmountTransaction(transaction)) {
				accountRs.getViolations().add(ViolationsEnum.DOUBLE_TRANSACTION.getDescripcion());
			}
			else {
				listTransaction.add(transaction);
			}
			accountRs.setAccount(acc);
		}

		return accountRs;
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	private boolean validateId(Long id) {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		for(AccountRs accRs:listAccount) {
			Account acc=accRs.getAccount();
			if(acc.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Obtener cuenta por id 
	 * @param id
	 * @return
	 */
	private Optional<AccountRs> getAccountId(Long id) {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
	  Optional<AccountRs> account=listAccount.stream().
			  filter(acc->acc.getAccount().getId()==id).findFirst();
	  
	  return account;
	}
	/**
	 * El monto de la transacción no debe exceder el límite disponible
	 * @param id
	 * @return
	 */
	private Long amountLimitAccountTransaction(Long id) {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.info("listransaction {}",listTransaction);
		  Long sumAmountTransaction=listTransaction.stream().
				  filter(tr->tr.getId()==id)
				  .mapToLong(tr->tr.getAmount()).sum();
		  logger.info("value {}",sumAmountTransaction);
		  return sumAmountTransaction;
	}
	
	/**
	 * No debe haber más de 3 transacciones en un intervalo de 2 minutos:
     * intervalo pequeño de alta frecuencia (no se puede confiar en el orden de entrada ya 
     * que las transacciones pueden eventualmente estar fuera de orden respectivamente a sus tiempos)
	 * @return
	 */
	private Long countTransactionInterval2(String merchant){
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Long countDateTranMinutes= listTransaction.stream()
				.filter(tr->DateUtil.convertDateTransMinutes(tr.getTime())<=2L
				 && !tr.getMerchant().equals(merchant))
				.mapToLong(tr->tr.getId())
				.count();
		logger.info("countDateTranMinutes:{}",countDateTranMinutes);
		return countDateTranMinutes;
	}
	/**
	 * No debe haber más de 1 transacción similar (mismo monto y comerciante) en un Intervalo de 2 minutos:
	 * @param tran
	 * @return
	 */
	private boolean sameAmountTransaction(Transaction tran) {
		Long countDateTranMinutes= listTransaction.stream()
				.filter(tr->DateUtil.convertDateTransMinutes(tr.getTime())<=2L
						&& tr.getMerchant().equals(tran.getMerchant())
						&& tr.getAmount().equals(tran.getAmount())).count();
		return countDateTranMinutes>0;

	}
	

	

	

}
