package com.payday.accountms.service;

import com.payday.accountms.dto.BalanceReservedDto;

public interface BalanceReservedService {

    BalanceReservedDto create(BalanceReservedDto balanceReservedDto);

    BalanceReservedDto update(BalanceReservedDto balanceReservedDto);

    void balanceReleased(Long orderId, String toSymbol,Double price);
}
