package com.payday.orderms.service.impl;

import com.payday.orderms.entity.OrderTransaction;
import com.payday.orderms.repository.OrderTransactionsRepository;
import com.payday.orderms.service.OrderTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderTransactionServiceImpl implements OrderTransactionService {

    private final OrderTransactionsRepository orderTransactionsRepository;

    @Override
    public OrderTransaction create(OrderTransaction orderTransactions) {
        return orderTransactionsRepository.save(orderTransactions);
    }

    @Override
    public Optional<OrderTransaction> findById(Long id) {
        return orderTransactionsRepository.findById(id);
    }

    @Override
    public List<OrderTransaction> findAll() {
        return orderTransactionsRepository.findAll();
    }

    @Override
    public OrderTransaction update(OrderTransaction orderTransactions) {
        if (orderTransactionsRepository.existsById(orderTransactions.getId())) {
            return orderTransactionsRepository.save(orderTransactions);
        } else {
            throw new IllegalArgumentException("OrderTransactions with ID " + orderTransactions.getId() + " not found");
        }
    }

    @Override
    public void delete(Long id) {
        orderTransactionsRepository.deleteById(id);
    }
}