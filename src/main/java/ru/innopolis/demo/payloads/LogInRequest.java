package ru.innopolis.demo.payloads;

import lombok.Data;

@Data
public class LogInRequest {

    private String email;

    private String password;
}
