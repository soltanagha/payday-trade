package com.payday.iam.service.impl;

import com.payday.common.event.dto.factory.EventFactory;
import com.payday.common.event.dto.iam.UserDto;
import com.payday.common.event.enumeration.EventType;
import com.payday.common.event.producer.EventProducer;
import com.payday.common.exception.BadRequestException;
import com.payday.common.exception.util.ExceptionCodes;
import com.payday.iam.auth.dto.request.SignUpRequest;
import com.payday.iam.auth.dto.request.SigninRequest;
import com.payday.iam.auth.dto.response.JwtAuthenticationResponse;
import com.payday.iam.entity.Role;
import com.payday.iam.entity.User;
import com.payday.iam.event.dto.EmailVerificationEventDto;
import com.payday.iam.repository.UserRepository;
import com.payday.iam.service.AuthenticationService;
import com.payday.iam.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final EventProducer<EmailVerificationEventDto> emailVerificationEventProducer;
    private final EventFactory<EmailVerificationEventDto> eventFactory;


    @Override
    public String signup(SignUpRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new BadRequestException(ExceptionCodes.DUPLICATE_DATA_EXCEPTION);

        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).isVerified(false).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);

        UserDto userDto = new UserDto(request.getEmail());
        var userCreatedEventDto = EmailVerificationEventDto
                .builder()
                .eventId(UUID.randomUUID().toString())
                .userDto(userDto)
                .time(Instant.now())
                .timezone(ZoneOffset.UTC.getId())
                .build();

        var emailVerificationEvent = eventFactory.createEvent(EventType.EMAIL_VERIFICATION.getName(),
                EventType.EMAIL_VERIFICATION.getTopic(), userCreatedEventDto);

        emailVerificationEventProducer.produce(emailVerificationEvent);

        return "Account registered, verification e-mail sent!";
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException(ExceptionCodes.USER_NOT_FOUND));

        if (!user.isVerified())
            throw new BadRequestException(ExceptionCodes.EMAIL_NOT_VERIFIED);

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
