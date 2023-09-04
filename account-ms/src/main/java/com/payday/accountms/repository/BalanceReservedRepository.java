package com.payday.accountms.repository;

import com.payday.accountms.entity.BalanceReserved;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceReservedRepository extends JpaRepository<BalanceReserved, Long> {

    Optional<BalanceReserved> findByOrderIdAndIsOpen(Long orderId,boolean isOpen);
}
