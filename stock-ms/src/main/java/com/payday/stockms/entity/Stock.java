package com.payday.stockms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {
    private String stockId;
    private String symbol;
    private double price;
}
