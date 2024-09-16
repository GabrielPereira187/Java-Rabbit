package br.com.rabbit.entities;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Product {
    private String description;
    private Integer quantity;
    private BigDecimal price;
}
