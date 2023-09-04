package com.payday.orderms.service;

import com.payday.orderms.dto.OrderDto;
import com.payday.orderms.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    OrderDto createOrder(OrderDto orderDto);
    Order updateOrder(Order order);
    List<Order> findOrders(String symbol, double price);
    void orderBalanceReserved(Long orderId);
}
