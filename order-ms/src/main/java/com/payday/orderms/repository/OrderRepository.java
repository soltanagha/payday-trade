package com.payday.orderms.repository;

import com.payday.orderms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.isOpen AND o.isReserved " +
            "AND ((o.price <= :price AND o.orderType = 'SELL' AND o.fromSymbol = :symbol) " +
            "OR (o.price >= :price AND o.orderType = 'BUY' AND o.toSymbol = :symbol))")
    List<Order> findOrders(@Param("symbol") String symbol, @Param("price") double price);

}
