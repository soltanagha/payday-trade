package com.payday.accountms.service.impl;

import com.payday.accountms.dto.AccountDto;
import com.payday.accountms.entity.Account;
import com.payday.accountms.entity.Balance;
import com.payday.accountms.repository.AccountRepository;
import com.payday.accountms.service.AccountService;
import com.payday.common.exception.BadRequestException;
import com.payday.common.exception.util.ExceptionCodes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        validateAccountCreation(accountDto);

        Account account = buildAccountFromDto(accountDto);
        List<Balance> initialBalances = createInitialBalances(account);

        saveAccountWithBalances(account, initialBalances);

        return accountDto;
    }

    private void validateAccountCreation(AccountDto accountDto) {
        if (isAccountEmailExist(accountDto.getEmail())) {
            throw new BadRequestException(ExceptionCodes.METHOD_ARGUMENT_NOT_VALID);
        }
    }

    private boolean isAccountEmailExist(String email) {
        return accountRepository.findAccountByEmail(email).isPresent();
    }

    private Account buildAccountFromDto(AccountDto accountDto) {
        return Account.builder().email(accountDto.getEmail()).build();
    }

    private List<Balance> createInitialBalances(Account account) {
        String[] initialSymbols = {"USD", "AZN", "EUR"};
        return Arrays.stream(initialSymbols)
                .map(symbol -> Balance.builder().account(account).symbol(symbol).amount(1000.00).build())
                .collect(Collectors.toList());
    }

    private void saveAccountWithBalances(Account account, List<Balance> initialBalances) {
        if (account.getBalance() == null) {
            account.setBalance(new ArrayList<>());
        }
        account.getBalance().addAll(initialBalances);
        accountRepository.save(account);
    }
    @Override
    public Optional<Account> getAccountByEmail(String email) {
        var optionalAccount = accountRepository.findAccountByEmail(email);

        if (optionalAccount.isEmpty()) {
            throw new BadRequestException(ExceptionCodes.USER_NOT_FOUND);
        }

        return optionalAccount;
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }
}