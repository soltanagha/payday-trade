package com.payday.accountms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_id_seq")
    @SequenceGenerator(name = "balance_id_seq", sequenceName = "balance_id_seq", allocationSize = 1)
    private Long id;
    private String symbol;
    private double amount;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonManagedReference
    @OneToMany(mappedBy = "balance", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BalanceReserved> balanceReserved = new ArrayList<>();


    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", amount=" + amount +
                '}';
    }
}