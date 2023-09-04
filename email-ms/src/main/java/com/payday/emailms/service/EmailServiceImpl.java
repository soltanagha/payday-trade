package com.payday.emailms.service;

import com.payday.common.dto.EmailDto;
import com.payday.common.event.dto.factory.EventFactory;
import com.payday.common.event.enumeration.EventType;
import com.payday.common.event.producer.EventProducer;
import com.payday.common.exception.NotFoundException;
import com.payday.common.exception.util.ExceptionCodes;
import com.payday.emailms.entity.Email;
import com.payday.emailms.event.dto.EmailVerifiedEventDto;
import com.payday.emailms.event.dto.UserDto;
import com.payday.emailms.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String username;
    @Value("${payday.contextPath}")
    private String baseUrl;
    private final EventProducer<EmailVerifiedEventDto> emailVerifiedEventProducer;
    private final EventFactory<EmailVerifiedEventDto> eventFactory;

    private final JavaMailSender emailSender;
    private final EmailRepository emailRepository;

    @Override
    public Email insert(EmailDto emailDto) {
        Email email = new Email();
        email.setTo(emailDto.getTo());
        email.setSubject("Email verification");
        email.setActivationKey(UUID.randomUUID().toString());
        email.setIsActive(false);
        email = emailRepository.save(email);
        return email;
    }

    @Override
    public EmailDto sendActivationLink(EmailDto emailDto) {

        var email = insert(emailDto);

        SimpleMailMessage message = new SimpleMailMessage();
        String activationLink = constructActivationUrl(email.getActivationKey());
        message.setTo(emailDto.getTo());
        message.setSubject(email.getSubject());
        message.setFrom(username);
        message.setText("Click the following link to activate your account: " + activationLink);
        emailSender.send(message);
        return emailDto;
    }

    @Override
    public Boolean activateUser(String activationKey) {
        Email email = emailRepository.findByActivationKeyAndIsActive(activationKey, false);
        if (!email.getTo().isEmpty()) {
            produceEvent(email.getTo());
            log.info("Email verified and produced event:"+email.getTo());
            return true;
        } else
            throw new NotFoundException(ExceptionCodes.INVALID_ACTIVATION_KEY);
    }

    void produceEvent(String email) {
        var userDto = UserDto.builder().email(email).build();
        var emailVerifiedEventDto = EmailVerifiedEventDto
                .builder()
                .eventId(UUID.randomUUID().toString())
                .userDto(userDto)
                .time(Instant.now())
                .timezone(ZoneOffset.UTC.getId())
                .build();

        var emailVerifiedEvent = eventFactory.createEvent(EventType.EMAIL_VERIFIED.getName(),
                EventType.EMAIL_VERIFIED.getTopic(), emailVerifiedEventDto);

        emailVerifiedEventProducer.produce(emailVerifiedEvent);
    }

    private String constructActivationUrl(String activationKey) {
        return baseUrl + "/api/email/activate?activation_key=" + activationKey;
    }

}
