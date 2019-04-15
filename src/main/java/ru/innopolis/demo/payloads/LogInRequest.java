package ru.innopolis.demo.payloads;

import lombok.Data;

/**
 * This payload is used for log in requests to transmit credentials
 */
@Data
public class LogInRequest {

    private String email;

    private String password;
}
