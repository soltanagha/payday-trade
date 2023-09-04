package com.payday.stockms;

import com.payday.common.event.dto.factory.impl.EventFactoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        EventFactoryImpl.class
})
public class StockMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockMsApplication.class, args);
    }

}
