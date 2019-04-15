package ru.innopolis.demo.payloads;

import lombok.Data;

/**
 * This payload is used to transmit new password in order to update it
 */
@Data
public class PasswordPayload {
    String password;
}
