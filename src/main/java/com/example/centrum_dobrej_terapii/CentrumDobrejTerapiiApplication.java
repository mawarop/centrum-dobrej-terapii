package com.example.centrum_dobrej_terapii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableSwagger2
public class CentrumDobrejTerapiiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentrumDobrejTerapiiApplication.class, args);

    }
}
