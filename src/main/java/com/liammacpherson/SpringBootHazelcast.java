package com.liammacpherson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootHazelcast {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHazelcast.class, args);
    }

}
