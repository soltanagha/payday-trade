package com.payday.accountms.dto.mapper;

import com.payday.accountms.dto.BalanceDto;
import com.payday.accountms.entity.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AccountMapper.class, BalanceReservedMapper.class })
public interface BalanceMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "symbol", target = "symbol")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "account", target = "accountDto")
    @Mapping(source = "balanceReserved", target = "balanceReservedDto")
    BalanceDto toDto(Balance balance);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "symbol", target = "symbol")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "accountDto", target = "account")
    @Mapping(source = "balanceReservedDto", target = "balanceReserved")
    Balance toEntity(BalanceDto balanceDto);

    List<BalanceDto> toDtoList(List<Balance> balances);
    List<Balance> toEntityList(List<BalanceDto> balanceDtos);
}