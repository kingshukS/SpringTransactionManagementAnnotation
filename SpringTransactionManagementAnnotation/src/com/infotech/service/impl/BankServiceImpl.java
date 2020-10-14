package com.infotech.service.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infotech.dao.BankDao;
import com.infotech.exception.InsufficientAccountBalanceException;
import com.infotech.model.Account;
import com.infotech.service.BankService;

public class BankServiceImpl implements BankService {

	private BankDao bankDao;

	public BankDao getBankDao() {
		return bankDao;
	}

	public void setBankDao(BankDao bankDao) {
		this.bankDao = bankDao;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, timeout = 100, rollbackFor = Exception.class)
	public void transferFund(Account fromAccount, Account toAccount, Double amount)
			throws InsufficientAccountBalanceException {
		this.getBankDao().withdraw(fromAccount, amount);
		this.getBankDao().deposit(toAccount, amount);

	}

}
