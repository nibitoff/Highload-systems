package com.alsab.boozycalc.party;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PartyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PartyServiceApplication.class, args);
    }
}
