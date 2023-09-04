package com.payday.accountms.dto.mapper;

import com.payday.accountms.dto.BalanceReservedDto;
import com.payday.accountms.entity.BalanceReserved;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// BalanceReservedMapper
@Mapper(componentModel = "spring")
public interface BalanceReservedMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "reservedAmount", target = "reservedAmount")
    @Mapping(source = "open", target = "isOpen")
    @Mapping(source = "balance", target = "balanceDto")
    BalanceReservedDto toDto(BalanceReserved balanceReserved);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "reservedAmount", target = "reservedAmount")
    @Mapping(source = "open", target = "isOpen")
    @Mapping(source = "balanceDto", target = "balance")
    BalanceReserved toEntity(BalanceReservedDto balanceReservedDto);

    List<BalanceReservedDto> toDtoList(List<BalanceReserved> balanceReserved);
    List<BalanceReserved> toEntityList(List<BalanceReservedDto> balanceReservedDtos);
}