package com.infotech.service;

import com.infotech.exception.InsufficientAccountBalanceException;
import com.infotech.model.Account;

public interface BankService {
	
	void transferFund(Account fromAccount, Account toAccount, Double amount) throws InsufficientAccountBalanceException;
}
