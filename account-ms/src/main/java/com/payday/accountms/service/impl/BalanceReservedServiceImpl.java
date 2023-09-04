package com.payday.accountms.service.impl;

import com.payday.accountms.dto.BalanceReservedDto;
import com.payday.accountms.dto.mapper.BalanceReservedMapper;
import com.payday.accountms.entity.Account;
import com.payday.accountms.entity.Balance;
import com.payday.accountms.entity.BalanceReserved;
import com.payday.accountms.repository.BalanceReservedRepository;
import com.payday.accountms.service.BalanceReservedService;
import com.payday.accountms.service.BalanceService;
import com.payday.common.exception.BalanceNotFoundException;
import com.payday.common.exception.util.ExceptionCodes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BalanceReservedServiceImpl implements BalanceReservedService {

    private final BalanceReservedRepository reservedRepository;
    private final BalanceReservedMapper balanceReservedMapper;
    private final BalanceService balanceService;

    @Override
    public BalanceReservedDto create(BalanceReservedDto balanceReservedDto) {
        var balanceReserved = balanceReservedMapper.toEntity(balanceReservedDto);
        reservedRepository.save(balanceReserved);
        return balanceReservedMapper.toDto(balanceReserved);
    }

    @Override
    public BalanceReservedDto update(BalanceReservedDto balanceReservedDto) {
        return null;
    }

    @Override
    @Transactional
    public void balanceReleased(Long orderId, String toSymbol, Double price) {
        var balanceReserved = findReservedBalance(orderId);
        closeReservedBalance(balanceReserved);

        var account = balanceReserved.getBalance().getAccount();
        var balanceTo = findOrCreateBalanceTo(account, toSymbol);

        updateBalanceTo(balanceTo, balanceReserved.getReservedAmount(), price, toSymbol);

        reservedRepository.save(balanceReserved);
        balanceService.create(balanceTo);
    }

    private BalanceReserved findReservedBalance(Long orderId) {
        return reservedRepository.findByOrderIdAndIsOpen(orderId, true)
                .orElseThrow(() -> new BalanceNotFoundException(ExceptionCodes.RESERVED_BALANCE_DOES_NOT_EXIST));
    }

    private void closeReservedBalance(BalanceReserved balanceReserved) {
        balanceReserved.setOpen(false);
    }

    private Balance findOrCreateBalanceTo(Account account, String toSymbol) {
        return account.getBalance().stream()
                .filter(bal -> bal.getSymbol().equals(toSymbol))
                .findFirst()
                .orElseGet(() -> createNewBalance(account, toSymbol));
    }

    private Balance createNewBalance(Account account, String toSymbol) {
        var newBalance = new Balance();
        newBalance.setAmount(0.0);
        newBalance.setAccount(account);
        return newBalance;
    }

    private void updateBalanceTo(Balance balanceTo, Double reservedAmount, Double price, String toSymbol) {
        var amount = reservedAmount / price;
        balanceTo.setSymbol(toSymbol);
        balanceTo.setAmount(balanceTo.getAmount() + amount);
    }
}
