package com.payday.emailms.repository;

import com.payday.emailms.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
    Email findByActivationKeyAndIsActive(String key, Boolean isActive);

}
