package com.payday.emailms.service;

import com.payday.common.dto.EmailDto;
import com.payday.emailms.entity.Email;

public interface EmailService {
    Email insert(EmailDto emailDto);

    EmailDto sendActivationLink(EmailDto emailDto);

    Boolean activateUser(String activationKey);
}
