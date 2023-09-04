package com.payday.accountms.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.payday.accountms.dto.BalanceDto;
import com.payday.accountms.dto.mapper.BalanceMapper;
import com.payday.accountms.entity.Balance;
import com.payday.accountms.repository.BalanceRepository;
import com.payday.common.event.dto.account.BalanceReservedEventDto;
import com.payday.common.event.dto.factory.EventFactory;
import com.payday.common.event.producer.EventProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class BalanceServiceImplTest {

    @InjectMocks
    private BalanceServiceImpl balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private BalanceMapper balanceMapper;

    @Mock
    private EventProducer<BalanceReservedEventDto> eventProducer;

    @Mock
    private EventFactory<BalanceReservedEventDto> eventFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBalance() {
        Balance balance = mock(Balance.class);
        BalanceDto balanceDto = mock(BalanceDto.class);

        when(balanceMapper.toDto(balance)).thenReturn(balanceDto);
        when(balanceRepository.save(balance)).thenReturn(balance);

        BalanceDto result = balanceService.create(balance);

        assertNotNull(result);
        assertEquals(balanceDto, result);
    }

    @Test
    void testFindAll() {
        List<Balance> balances = new ArrayList<>();
        balances.add(mock(Balance.class));

        List<BalanceDto> balanceDtos = new ArrayList<>();
        balanceDtos.add(mock(BalanceDto.class));

        when(balanceRepository.findAll()).thenReturn(balances);
        when(balanceMapper.toDto(any(Balance.class))).thenReturn(balanceDtos.get(0));

        List<BalanceDto> result = balanceService.findAll();

        assertEquals(1, result.size());
        assertEquals(balanceDtos, result);
    }

}