package com.payday.orderms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_transaction")
public class OrderTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_transaction_id_seq")
    @SequenceGenerator(name = "order_transaction_id_seq", sequenceName = "order_transaction_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "transaction_timestamp")
    private LocalDateTime transactionTimestamp;

    @Column(name = "transaction_price")
    private double transactionPrice;
}
