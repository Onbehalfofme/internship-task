package ru.innopolis.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TaskApplication {

    private static String[] args;
    private static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        TaskApplication.args = args;
        TaskApplication.context = SpringApplication.run(TaskApplication.class, args);
    }

}
