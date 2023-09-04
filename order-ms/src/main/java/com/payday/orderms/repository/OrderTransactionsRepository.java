package com.payday.orderms.repository;

import com.payday.orderms.entity.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTransactionsRepository extends JpaRepository<OrderTransaction, Long> {
}
