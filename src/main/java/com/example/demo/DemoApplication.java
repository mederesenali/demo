package com.example.demo;

import com.example.demo.service.DBInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DemoApplication.class, args);
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
        applicationContext.getBean(DBInitializer.class).initDB();
    }

}
