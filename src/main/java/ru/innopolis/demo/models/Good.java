package ru.innopolis.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *  Model contains information about goods
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(unique = true)
    @Min(0)
    private int price;

    @NotNull
    @Size(max = 16)
    private String category;

    @NotNull
    @Size(max = 32)
    private String name;

    @NotNull
    @Min(0)
    private int quantity;

    private String description;
}
