package com.payday.orderms.service;

import com.payday.orderms.entity.OrderTransaction;

import java.util.List;
import java.util.Optional;

public interface OrderTransactionService {
    OrderTransaction create(OrderTransaction orderTransactio);

    Optional<OrderTransaction> findById(Long id);

    List<OrderTransaction> findAll();

    OrderTransaction update(OrderTransaction orderTransaction);

    void delete(Long id);
}
