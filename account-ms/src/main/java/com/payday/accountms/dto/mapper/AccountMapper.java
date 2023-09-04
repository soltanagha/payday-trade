package com.payday.accountms.dto.mapper;

import com.payday.accountms.dto.AccountDto;
import com.payday.accountms.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "email", target = "email")
    @Mapping(source = "balance", target = "balance")
    AccountDto toDto(Account account);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "balance", target = "balance")
    Account toEntity(AccountDto accountDto);

    List<AccountDto> toDtoList(List<Account> accounts);
    List<Account> toEntityList(List<AccountDto> accountDtos);
}