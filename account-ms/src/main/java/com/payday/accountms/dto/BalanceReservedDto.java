package com.payday.accountms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceReservedDto {
        private Long id;
        private Long orderId;
        private double reservedAmount;
        private boolean isOpen;
        private BalanceDto balanceDto;
}
