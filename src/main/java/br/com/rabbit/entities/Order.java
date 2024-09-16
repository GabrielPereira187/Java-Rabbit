package br.com.rabbit.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "orderList")
@Data
public class Order {

    @Id
    private UUID orderId;
    private Long customerId;
    private ArrayList<Product> items;
    private BigDecimal finalPrice;


    public BigDecimal calculateFinalPrice() {
        return this.items.stream().
                map(i -> BigDecimal.valueOf(i.getQuantity()).multiply(i.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
