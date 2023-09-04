package com.payday.accountms.repository;

import com.payday.accountms.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Query("SELECT b FROM Balance b LEFT JOIN FETCH b.account a WHERE a.email = :email AND b.symbol = :symbol")
    Optional<Balance> findByEmailAndSymbolWithAccount(@Param("email") String email, @Param("symbol") String symbol);
}
