package ru.innopolis.demo.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * this payload is used to transmit all needed information about signed up user
 */
@Data
public class SignUpRequest {
    private String email;

    private String password;

    private String name;

    private String surname;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;
}
