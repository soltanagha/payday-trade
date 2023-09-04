package com.payday.orderms.dto;

import com.payday.orderms.entity.OrderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;

    @NotBlank(message = "From Symbol is required")
    private String fromSymbol;

    @NotBlank(message = "To Symbol is required")
    private String toSymbol;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private double price;

    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private double amount;

    @NotNull(message = "Order type is required")
    private OrderType orderType;

    private Boolean isOpen;

    private Boolean isReserved;
}
