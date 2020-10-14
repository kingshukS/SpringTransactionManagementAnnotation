package com.infotech.client;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.infotech.exception.InsufficientAccountBalanceException;
import com.infotech.model.Account;
import com.infotech.service.BankService;
import com.infotech.service.impl.BankServiceImpl;

public class TestClient {

	public static void main(String[] args) {
		
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		BankService bankService = ctx.getBean("bankService",BankServiceImpl.class);
		
		Account fromAccount = new Account();
		fromAccount.setAccountNumber(69442L);
		
		Account toAccount = new Account();
		toAccount.setAccountNumber(143264L);
		
		try {
			bankService.transferFund(fromAccount, toAccount, 1000.00);
		} catch (InsufficientAccountBalanceException e) {
			
			e.printStackTrace();
		}
	}

}
