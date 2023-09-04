package com.payday.common.event.dto.account;

public record BalanceReleaseDto(Long orderId,String email, String toSymbol,Double price) {
}
