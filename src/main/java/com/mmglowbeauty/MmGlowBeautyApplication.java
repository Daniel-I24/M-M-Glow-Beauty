package com.mmglowbeauty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MmGlowBeautyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmGlowBeautyApplication.class, args);
    }
}
