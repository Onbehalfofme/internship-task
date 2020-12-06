package ru.innopolis.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.demo.converters.DateConverter;

import javax.persistence.*;

import java.time.LocalDate;
/**
 *  Model contains information about users in the system
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String surname;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Convert(converter = DateConverter.class)
    private LocalDate birthday;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Override
    public String toString() {
        return "Email " + email +" Name is "+ name + " Surname is " + surname;
    }

}
