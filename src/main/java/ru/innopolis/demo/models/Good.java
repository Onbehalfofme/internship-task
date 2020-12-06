package ru.innopolis.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


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

    @Column(unique = true)
    private int price;

    private String category;

    private String name;

    private int quantity;

    private String description;
}
