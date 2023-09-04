package com.payday.orderms.service.impl;

import com.payday.common.event.dto.account.BalanceReleaseDto;
import com.payday.common.event.dto.account.BalanceReleaseEventDto;
import com.payday.common.event.dto.factory.EventFactory;
import com.payday.common.event.dto.stock.StockDto;
import com.payday.common.event.dto.stock.StockUpdateEventDto;
import com.payday.common.event.enumeration.EventType;
import com.payday.common.event.producer.EventProducer;
import com.payday.orderms.entity.Order;
import com.payday.orderms.entity.OrderTransaction;
import com.payday.orderms.entity.OrderType;
import com.payday.orderms.service.OrderService;
import com.payday.orderms.service.OrderTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StockPriceUpdateService implements com.payday.orderms.service.StockPriceUpdateService {

    private final OrderService orderService;
    private final OrderTransactionService orderTransactionService;
    private final EventProducer<BalanceReleaseEventDto> balanceReleaseEventDtoEventProducer;
    private final EventFactory<BalanceReleaseEventDto> eventFactory;

    @Override
    public void processOrders(StockUpdateEventDto StockUpdateEventDto) {
        if (StockUpdateEventDto == null || StockUpdateEventDto.getStockDtoList() == null) return;

        for (StockDto stockDto : StockUpdateEventDto.getStockDtoList()) {
            List<Order> orders = orderService.findOrders(stockDto.getSymbol(), stockDto.getPrice());

            for (Order order : orders) {
                processOrder(stockDto, order);
            }
        }
    }
    public void processOrder(StockDto stockDto, Order order) {
        if (isInvalidOrder(stockDto, order)) return;

        if (isBuyOrder(order) && isBuyPriceValid(order, stockDto)) {
            processBuyOrSellOrder(stockDto, order);
        } else if (isSellOrder(order) && isSellPriceValid(order, stockDto)) {
            processBuyOrSellOrder(stockDto, order);
        }
    }

    private boolean isInvalidOrder(StockDto stockDto, Order order) {
        return stockDto == null || order == null || !order.getIsReserved();
    }

    private boolean isBuyOrder(Order order) {
        return order.getOrderType() == OrderType.BUY;
    }

    private boolean isSellOrder(Order order) {
        return order.getOrderType() == OrderType.SELL;
    }

    private boolean isBuyPriceValid(Order order, StockDto stockDto) {
        return order.getPrice() >= stockDto.getPrice();
    }

    private boolean isSellPriceValid(Order order, StockDto stockDto) {
        return order.getPrice() <= stockDto.getPrice();
    }

    private void processBuyOrSellOrder(StockDto stockDto, Order order) {
        produceEvent(order);
        updateOrderTransaction(stockDto, order);
    }

    private void produceEvent(Order order) {
        produceEvent(order.getId(), order.getAccountEmail(), order.getToSymbol(), order.getPrice());
    }

    private void updateOrderTransaction(StockDto stockDto, Order order) {
        var orderTransaction = createOrderTransaction(stockDto, order);
        orderTransactionService.create(orderTransaction);
        closeOrder(order);
    }

    private OrderTransaction createOrderTransaction(StockDto stockDto, Order order) {
        return OrderTransaction.builder()
                .order(order)
                .transactionPrice(stockDto.getPrice())
                .transactionTimestamp(LocalDateTime.now())
                .build();
    }

    private void closeOrder(Order order) {
        order.setIsOpen(false);
        orderService.updateOrder(order);
    }

    private void produceEvent(Long orderId, String email, String symbol, Double price) {
        var balanceReleaseDto = new BalanceReleaseDto(orderId, email, symbol, price);
        var balanceReleaseEventDto = createBalanceReleaseEventDto(balanceReleaseDto);

        var balanceSufficientEvent = eventFactory.createEvent(EventType.BALANCE_RELEASE.getName(),
                EventType.BALANCE_RELEASE.getTopic(), balanceReleaseEventDto);

        balanceReleaseEventDtoEventProducer.produce(balanceSufficientEvent);
    }

    private BalanceReleaseEventDto createBalanceReleaseEventDto(BalanceReleaseDto balanceReleaseDto) {
        return BalanceReleaseEventDto.builder()
                .eventId(UUID.randomUUID().toString())
                .balanceReleaseDto(balanceReleaseDto)
                .time(Instant.now())
                .timezone(ZoneOffset.UTC.getId())
                .build();
    }
}
