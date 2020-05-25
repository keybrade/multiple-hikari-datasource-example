package com.example.multiplehikaridatasourcedemo;

import com.example.multiplehikaridatasourcedemo.repository.bar.BarRepository;
import com.example.multiplehikaridatasourcedemo.repository.foo.FooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MultipleHikariDatasourceDemoApplication {

    @Autowired
    private BarRepository barRepository;
    @Autowired
    private FooRepository fooRepository;

    public static void main(String[] args) {
        SpringApplication.run(MultipleHikariDatasourceDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return (args) -> {
            barRepository.findAll();
            fooRepository.findAll();
        };
    }
}
