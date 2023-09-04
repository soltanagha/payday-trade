package com.payday.emailms.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.payday.common.dto.EmailDto;
import com.payday.common.event.dto.factory.EventFactory;
import com.payday.common.event.producer.EventProducer;
import com.payday.common.exception.NotFoundException;
import com.payday.emailms.entity.Email;
import com.payday.emailms.event.dto.EmailVerifiedEventDto;
import com.payday.emailms.repository.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EventProducer<EmailVerifiedEventDto> emailVerifiedEventProducer;

    @Mock
    private EventFactory<EmailVerifiedEventDto> eventFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsert() {
        EmailDto emailDto = new EmailDto();
        emailDto.setTo("test@example.com");

        Email email = new Email();
        email.setTo(emailDto.getTo());

        when(emailRepository.save(any(Email.class))).thenReturn(email);

        Email result = emailService.insert(emailDto);

        assertNotNull(result);
        assertEquals(emailDto.getTo(), result.getTo());
    }

    @Test
    void testSendActivationLink() {
        EmailDto emailDto = new EmailDto();
        emailDto.setTo("test@example.com");

        Email email = new Email();
        email.setTo(emailDto.getTo());
        email.setActivationKey(UUID.randomUUID().toString());

        when(emailRepository.save(any(Email.class))).thenReturn(email);

        EmailDto result = emailService.sendActivationLink(emailDto);

        assertNotNull(result);
        assertEquals(emailDto.getTo(), result.getTo());
    }

    @Test
    void testActivateUser() {
        String activationKey = UUID.randomUUID().toString();
        Email email = new Email();
        email.setTo("test@example.com");

        when(emailRepository.findByActivationKeyAndIsActive(activationKey, false)).thenReturn(email);

        Boolean result = emailService.activateUser(activationKey);

        assertTrue(result);
    }

    @Test
    void testActivateUserNotFound() {
        String activationKey = UUID.randomUUID().toString();

        Email email = new Email();
        email.setTo("");

        when(emailRepository.findByActivationKeyAndIsActive(activationKey, false)).thenReturn(email);

        assertThrows(NotFoundException.class, () -> emailService.activateUser(activationKey));
    }
}
