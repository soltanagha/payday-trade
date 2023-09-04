package com.payday.accountms.service;

import com.payday.accountms.dto.AccountDto;
import com.payday.accountms.entity.Account;

import java.util.Optional;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    Optional<Account> getAccountByEmail(String email);

    void updateAccount(Account account);
}
