package com.payday.iam.service.impl;

import com.payday.common.event.dto.factory.EventFactory;
import com.payday.common.event.dto.iam.UserDto;
import com.payday.common.event.enumeration.EventType;
import com.payday.common.event.producer.EventProducer;
import com.payday.common.exception.BadRequestException;
import com.payday.common.exception.util.ExceptionCodes;
import com.payday.iam.event.dto.NewUserBalanceCreateEventDto;
import com.payday.iam.repository.UserRepository;
import com.payday.iam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final EventProducer<NewUserBalanceCreateEventDto> newUserBalanceCreateEventProducer;
    private final EventFactory<NewUserBalanceCreateEventDto> eventFactory;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new BadRequestException(ExceptionCodes.USER_NOT_FOUND));
            }
        };
    }

    @Override
    public void emailVerified(String email) {
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty())
            throw new BadRequestException(ExceptionCodes.USER_NOT_FOUND);

        var user = userOptional.get();
        user.setVerified(true);
        userRepository.save(user);
        UserDto userDto = new UserDto(user.getEmail());
        var userCreatedEventDto = NewUserBalanceCreateEventDto
                .builder()
                .eventId(UUID.randomUUID().toString())
                .userDto(userDto)
                .time(Instant.now())
                .timezone(ZoneOffset.UTC.getId())
                .build();

        var stockPriceUpdateEvent = eventFactory.createEvent(EventType.NEW_USER_BALANCE.getName(),
                EventType.NEW_USER_BALANCE.getTopic(), userCreatedEventDto);

        newUserBalanceCreateEventProducer.produce(stockPriceUpdateEvent);
    }
}
