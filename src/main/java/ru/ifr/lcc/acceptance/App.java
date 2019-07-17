package ru.ifr.lcc.acceptance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.ifr")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
