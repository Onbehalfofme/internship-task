package ru.innopolis.demo.payloads;

import lombok.Data;

/**
 * This class is used to transmit information about goods
 */
@Data
public class GoodPayload {

    private int price;

    private String category;

    private String name;

    private int quantity;

    private String description;
}
