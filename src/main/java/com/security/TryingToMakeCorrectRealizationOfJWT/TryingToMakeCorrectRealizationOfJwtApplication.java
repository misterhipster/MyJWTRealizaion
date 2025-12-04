package com.security.TryingToMakeCorrectRealizationOfJWT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.security.TryingToMakeCorrectRealizationOfJWT",
        "config"  // Убедитесь, что пакет config сканируется
})
public class TryingToMakeCorrectRealizationOfJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(TryingToMakeCorrectRealizationOfJwtApplication.class, args);
    }

}
