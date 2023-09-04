package com.payday.accountms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "balance_reserved")
public class BalanceReserved {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_reserved_id_seq")
    @SequenceGenerator(name = "balance_reserved_id_seq", sequenceName = "balance_reserved_id_seq", allocationSize = 1)
    private Long id;

    private Long orderId;
    private double reservedAmount;
    private boolean isOpen;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "balance_id")
    private Balance balance;

}