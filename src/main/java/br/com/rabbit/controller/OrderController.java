package br.com.rabbit.controller;

import br.com.rabbit.Consumer.Request.OrderRequestConsumer;
import br.com.rabbit.Producer.Request.OrderRequestProducer;
import br.com.rabbit.entities.Order;
import br.com.rabbit.entities.Response;
import br.com.rabbit.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderRequestProducer orderRequestProducer;
    private final OrderService orderService;
    private final OrderRequestConsumer orderRequestConsumer;

    @Autowired
    public OrderController(OrderRequestProducer orderRequestProducer, OrderService orderService, OrderRequestConsumer orderRequestConsumer) {
        this.orderRequestProducer = orderRequestProducer;
        this.orderService = orderService;
        this.orderRequestConsumer = orderRequestConsumer;
    }

    @GetMapping("/quantityPerCustomerId/{customerId}")
    public ResponseEntity<Object> getOrderQuantityByCustomerId(@PathVariable("customerId") Long customerId) {
        return orderService.getOrderQuantityByCustomerId(customerId);
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.findOrders();
    }

    @PostMapping("/order")
    public ResponseEntity<Object> postOrder(@RequestBody Order order) throws JsonProcessingException {
        return orderRequestProducer.sendOrderMessage(order);
    }

    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<Object> getTotalPerOrderId(@PathVariable("orderId") UUID orderId) {
        return orderService.getTotalValuePerOrderId(orderId);
    }

    @GetMapping("/getAllOrdersPerCustomer/{customerId}")
    public ResponseEntity<List<Order>> getAllOrdersPerCustomer(@PathVariable("customerId") Long customerId) throws Exception {
        return orderService.getAllOrdersPerCustomer(customerId);
    }

}
