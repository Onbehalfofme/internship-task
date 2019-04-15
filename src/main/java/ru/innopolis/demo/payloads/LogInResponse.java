package ru.innopolis.demo.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This payload is used to return JWT token in case of successful login
 */
@Data
@AllArgsConstructor
public class LogInResponse {
    private String token;
}
