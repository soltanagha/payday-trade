package com.payday.accountms.service;

import com.payday.accountms.dto.BalanceDto;
import com.payday.accountms.entity.Balance;

import java.util.List;

public interface BalanceService {
    BalanceDto create(Balance balance);

    List<BalanceDto> findAll();
    List<Balance> getAll();

    BalanceDto update(BalanceDto balance);

    void reserveBalance(Long orderId, String email, String symbol, Double amount);

}
