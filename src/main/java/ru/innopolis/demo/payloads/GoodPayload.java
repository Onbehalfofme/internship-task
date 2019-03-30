package ru.innopolis.demo.payloads;

import lombok.Data;

@Data
public class GoodPayload {

    private int price;

    private String category;

    private String name;

    private int quantity;

    private String description;
}
