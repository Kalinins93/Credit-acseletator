package ru.neoflex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MSApplication {
    public static void main (String [] args){
        SpringApplication.run(MSApplication.class, args);
    }
}
