package com.payday.iam;

import com.payday.common.event.dto.factory.impl.EventFactoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        EventFactoryImpl.class
})
public class IamMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IamMsApplication.class, args);
    }

}
