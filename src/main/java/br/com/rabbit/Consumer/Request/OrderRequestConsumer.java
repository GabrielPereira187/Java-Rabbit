package br.com.rabbit.Consumer.Request;

import br.com.rabbit.entities.Order;
import br.com.rabbit.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderRequestConsumer {

    private final OrderService orderService;

    @Autowired
    public OrderRequestConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = {"orderRequest-queue"})
    public void receive(@Payload Order order) {
        log.info("Received message: " + order);
        order.setOrderId(UUID.randomUUID());
        order.setFinalPrice(order.calculateFinalPrice());
        orderService.insertOrder(order);
    }
}
