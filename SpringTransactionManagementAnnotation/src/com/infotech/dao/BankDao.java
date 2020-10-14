package com.infotech.dao;

import com.infotech.exception.InsufficientAccountBalanceException;
import com.infotech.model.Account;

public interface BankDao {
	
	void withdraw(Account fromAccount, Double amount) throws InsufficientAccountBalanceException;
	void deposit(Account toAccount, Double amount);
	
}
