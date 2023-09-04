package com.payday.accountms.service.impl;

import com.payday.accountms.dto.BalanceDto;
import com.payday.accountms.dto.mapper.BalanceMapper;
import com.payday.accountms.entity.Balance;
import com.payday.accountms.entity.BalanceReserved;
import com.payday.accountms.repository.BalanceRepository;
import com.payday.accountms.service.BalanceService;
import com.payday.common.event.dto.account.BalanceReserveDto;
import com.payday.common.event.dto.account.BalanceReservedEventDto;
import com.payday.common.event.dto.factory.EventFactory;
import com.payday.common.event.enumeration.EventType;
import com.payday.common.event.producer.EventProducer;
import com.payday.common.exception.BadRequestException;
import com.payday.common.exception.BalanceNotFoundException;
import com.payday.common.exception.util.ExceptionCodes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;
    private final EventProducer<BalanceReservedEventDto> balanceReservedEventDtoEventProducer;
    private final EventFactory<BalanceReservedEventDto> eventFactory;

    @Override
    public BalanceDto create(Balance balance) {
        //var balance = mapToEntity(balanceDto);
        balanceRepository.save(balance);
        return mapToDto(balance);
    }

    @Override
    public List<BalanceDto> findAll() {
        return balanceRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Balance> getAll() {
        return new ArrayList<>(balanceRepository.findAll());
    }

    @Override
    public BalanceDto update(BalanceDto balanceDto) {
        if (balanceRepository.existsById(balanceDto.getId())) {
            return mapToDto(balanceRepository.save(mapToEntity(balanceDto)));
        } else {
            throw new BalanceNotFoundException("Balance with ID " + balanceDto.getId() + " not found",
                    ExceptionCodes.METHOD_ARGUMENT_NOT_VALID);
        }
    }

    @Override
    @Transactional
    public void reserveBalance(Long orderId, String email, String symbol, Double amount) {
        Balance balance = findBalanceByEmailAndSymbol(email, symbol);
        if (isSufficientBalance(balance, amount)) {
            updateBalanceAndCreateReservation(balance, orderId, amount);
            log.info("Balance reserved for process order");
        } else {
            handleInsufficientBalance();
        }
    }

    private Balance findBalanceByEmailAndSymbol(String email, String symbol) {
        return balanceRepository.findByEmailAndSymbolWithAccount(email, symbol)
                .orElseThrow(() -> new BadRequestException(ExceptionCodes.INVALID_EMAIL_OR_SYMBOL));
    }

    private boolean isSufficientBalance(Balance balance, Double amount) {
        return balance.getAmount() >= amount;
    }

    private void updateBalanceAndCreateReservation(Balance balance, Long orderId, Double amount) {
        reduceBalanceAmount(balance, amount);
        addBalanceReservation(balance, orderId, amount);
        produceReserveEvent(orderId, balance.getAccount().getEmail(), balance.getSymbol(), amount);
    }

    private void reduceBalanceAmount(Balance balance, Double amount) {
        balance.setAmount(balance.getAmount() - amount);
    }

    private void addBalanceReservation(Balance balance, Long orderId, Double amount) {
        var balanceReserved = BalanceReserved.builder()
                .orderId(orderId)
                .balance(balance)
                .reservedAmount(amount)
                .isOpen(true)
                .build();

        if (balance.getBalanceReserved() == null) {
            balance.setBalanceReserved(new ArrayList<>());
        }
        balance.getBalanceReserved().add(balanceReserved);
    }

    private void handleInsufficientBalance() {
        log.warn("Balance insufficient");
        // TODO: Produce balance insufficient event
    }

    void produceReserveEvent(Long orderId, String email, String symbol, Double amount) {
        var balanceReserveDto = new BalanceReserveDto(orderId, email, symbol, amount);
        var balanceReserveEventDto = BalanceReservedEventDto.builder()
                .eventId(UUID.randomUUID().toString())
                .balanceReserveDto(balanceReserveDto)
                .time(Instant.now())
                .timezone(ZoneOffset.UTC.getId())
                .build();

        var balanceSufficientEvent = eventFactory.createEvent(EventType.BALANCE_RESERVED.getName(),
                EventType.BALANCE_RESERVED.getTopic(), balanceReserveEventDto);

        balanceReservedEventDtoEventProducer.produce(balanceSufficientEvent);
    }

    private BalanceDto mapToDto(Balance balance) {
        return balanceMapper.toDto(balance);
    }

    private Balance mapToEntity(BalanceDto balanceDto) {
        return balanceMapper.toEntity(balanceDto);
    }

}
