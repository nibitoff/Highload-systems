package com.alsab.boozycalc.auth;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
public class R2DBCConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(PORT,  5432)
                        .option(DRIVER, "postgresql")
                        .option(HOST, "prod_db")
                        .option(USER, "docker_user")
                        .option(PASSWORD, "passphrase")
                        .option(DATABASE, "boozyCalc")
                        .build());
    }
}