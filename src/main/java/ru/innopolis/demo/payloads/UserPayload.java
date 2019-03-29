package ru.innopolis.demo.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.innopolis.demo.models.Role;
import ru.innopolis.demo.models.UserProfile;

import java.time.LocalDate;

@Data
public class UserPayload {

    private long id;

    private String email;

    private String name;

    private String surname;

    private Role role;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    public UserPayload(UserProfile userProfile) {
        id = userProfile.getId();
        email = userProfile.getEmail();
        name = userProfile.getName();
        surname = userProfile.getSurname();
        role = userProfile.getRole();
        birthday = userProfile.getBirthday();
    }
}