package com.infotech.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.infotech.dao.BankDao;
import com.infotech.exception.InsufficientAccountBalanceException;
import com.infotech.model.Account;
import com.infotech.rowmapper.AccountRowMapper;

public class BankDaoImpl implements BankDao {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void withdraw(Account fromAccount, Double withdrawAmt) throws InsufficientAccountBalanceException {
		Account accountFromDb = null;
		if (fromAccount != null && fromAccount.getAccountNumber() != null) {
			accountFromDb = getAccountFromDb(fromAccount.getAccountNumber());
		} else {
			throw new RuntimeException("Account Number doesn't exits");
		}

		if (accountFromDb.getAccountBalance() >= withdrawAmt) {
			Double accountBalance = accountFromDb.getAccountBalance() - withdrawAmt;
			String sql = "UPDATE icici_bank set account_balance=? WHERE account_no=?;";
			int update = getJdbcTemplate().update(sql, accountBalance, fromAccount.getAccountNumber());
			if (update > 0)
				System.out.println(
						"Amount Rs:" + withdrawAmt + " is deducted from account:" + fromAccount.getAccountNumber());
		} else {
			throw new InsufficientAccountBalanceException("Insufficient account balance");
		}
	}

	@Override
	public void deposit(Account toAccount, Double depositAmt) {
		Account accountFromDb = null;
		Double accountBalance = null;
		if (toAccount != null && toAccount.getAccountNumber() != null) {
			accountFromDb = getAccountFromDb(toAccount.getAccountNumber());
			accountBalance = accountFromDb.getAccountBalance() + depositAmt;
		} else {
			throw new RuntimeException("Account Number doesn't exits");
		}

		String sql = "UPDATE icici_bank set account_balance=? WHERE account_no=?;";
		int update = getJdbcTemplate().update(sql, accountBalance, toAccount.getAccountNumber());
		if (update > 0)
			System.out.println("Amount Rs: " + depositAmt + " is deposited in Account:" + toAccount.getAccountNumber());
	}

	private Account getAccountFromDb(Long accountNumber) {
		String SQL = "SELECT * FROM icici_bank WHERE account_no = ?";
		Account dDAccount = getJdbcTemplate().queryForObject(SQL, new AccountRowMapper(), accountNumber);
		return dDAccount;
	}

}
