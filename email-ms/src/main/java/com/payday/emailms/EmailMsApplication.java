package com.payday.emailms;

import com.payday.common.event.dto.factory.impl.EventFactoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        EventFactoryImpl.class
})
public class EmailMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailMsApplication.class, args);
    }

}
