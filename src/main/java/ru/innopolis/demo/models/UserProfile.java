package ru.innopolis.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.demo.converters.DateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @Column(unique = true)
    @Size(max = 32)
    private String email;

    @NotNull
    private String password;


    @NotNull
    @Size(max = 32)
    private String name;

    @NotNull
    @Size(max = 32)
    private String surname;

    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Convert(converter = DateConverter.class)
    private LocalDate birthday;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Role role;


    @Override
    public String toString() {
        return "Email " + email;
    }

}
