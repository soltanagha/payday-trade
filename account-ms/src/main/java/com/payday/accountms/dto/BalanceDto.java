package com.payday.accountms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDto {

    private Long id;
    private String symbol;
    private double amount;
    private AccountDto accountDto;
    private List<BalanceReservedDto> balanceReservedDto = new ArrayList<>();
    }