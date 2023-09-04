package com.payday.accountms.service.impl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.payday.accountms.dto.BalanceReservedDto;
import com.payday.accountms.dto.mapper.BalanceReservedMapper;
import com.payday.accountms.entity.Account;
import com.payday.accountms.entity.Balance;
import com.payday.accountms.entity.BalanceReserved;
import com.payday.accountms.repository.BalanceReservedRepository;
import com.payday.accountms.service.BalanceService;
import com.payday.common.exception.BalanceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BalanceReservedServiceImplTest {

    @Mock
    private BalanceReservedRepository reservedRepository;

    @Mock
    private BalanceReservedMapper balanceReservedMapper;

    @Mock
    private BalanceService balanceService;

    @InjectMocks
    private BalanceReservedServiceImpl balanceReservedService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        BalanceReservedDto balanceReservedDto = new BalanceReservedDto();
        BalanceReserved balanceReserved = new BalanceReserved();

        when(balanceReservedMapper.toEntity(balanceReservedDto)).thenReturn(balanceReserved);
        when(balanceReservedMapper.toDto(balanceReserved)).thenReturn(balanceReservedDto);

        BalanceReservedDto result = balanceReservedService.create(balanceReservedDto);

        assertEquals(balanceReservedDto, result);
        verify(reservedRepository, times(1)).save(balanceReserved);
    }

    @Test
    public void testUpdate() {
        // Method currently returns null, so test will confirm null returned
        assertNull(balanceReservedService.update(new BalanceReservedDto()));
    }

    @Test
    public void testBalanceReleased() {
        Long orderId = 1L;
        String toSymbol = "USD";
        Double price = 100.0;

        BalanceReserved balanceReserved = mock(BalanceReserved.class);
        Balance balance = mock(Balance.class);
        Account account = mock(Account.class);

        when(reservedRepository.findByOrderIdAndIsOpen(orderId, true)).thenReturn(Optional.of(balanceReserved));
        when(balanceReserved.getBalance()).thenReturn(balance);
        when(balance.getAccount()).thenReturn(account);

        balanceReservedService.balanceReleased(orderId, toSymbol, price);

        verify(reservedRepository, times(1)).save(balanceReserved);
        verify(balanceService, times(1)).create(any(Balance.class));
    }

    @Test
    public void testBalanceReleasedNotFound() {
        Long orderId = 1L;
        String toSymbol = "USD";
        Double price = 100.0;

        when(reservedRepository.findByOrderIdAndIsOpen(orderId, true))
                .thenReturn(Optional.empty());

        assertThrows(BalanceNotFoundException.class,
                () -> balanceReservedService.balanceReleased(orderId, toSymbol, price));
    }
}