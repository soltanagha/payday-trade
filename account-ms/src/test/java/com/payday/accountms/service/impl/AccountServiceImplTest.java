package com.payday.accountms.service.impl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.payday.accountms.dto.AccountDto;
import com.payday.accountms.entity.Account;
import com.payday.accountms.repository.AccountRepository;
import com.payday.common.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount() {
        // Given
        AccountDto accountDto = new AccountDto("email@example.com",new ArrayList<>());
        when(accountRepository.findAccountByEmail("email@example.com")).thenReturn(Optional.empty());

        // When
        AccountDto createdAccountDto = accountService.createAccount(accountDto);

        // Then
        assertEquals("email@example.com", createdAccountDto.getEmail());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testCreateAccountWithExistingEmail() {
        // Given
        AccountDto accountDto = new AccountDto("email@example.com",new ArrayList<>());
        when(accountRepository.findAccountByEmail("email@example.com")).thenReturn(Optional.of(new Account()));

        // Then
        assertThrows(BadRequestException.class, () -> accountService.createAccount(accountDto));
    }

    @Test
    public void testGetAccountByEmail() {
        // Given
        when(accountRepository.findAccountByEmail("email@example.com")).thenReturn(Optional.of(new Account()));

        // When
        Optional<Account> foundAccount = accountService.getAccountByEmail("email@example.com");

        // Then
        assertTrue(foundAccount.isPresent());
    }

    @Test
    public void testGetAccountByEmailNotFound() {
        // Given
        when(accountRepository.findAccountByEmail("email@example.com")).thenReturn(Optional.empty());

        // Then
        assertThrows(BadRequestException.class, () -> accountService.getAccountByEmail("email@example.com"));
    }

    @Test
    public void testUpdateAccount() {
        // Given
        Account account = new Account();

        // When
        accountService.updateAccount(account);

        // Then
        verify(accountRepository, times(1)).save(account);
    }
}