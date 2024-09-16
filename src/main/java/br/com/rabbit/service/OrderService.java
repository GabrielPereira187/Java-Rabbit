package br.com.rabbit.service;

import br.com.rabbit.Producer.Response.OrderResponseProducer;
import br.com.rabbit.entities.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.rabbit.entities.Order;
import br.com.rabbit.repositories.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    private OrderRepository orderRepository;

    private MongoTemplate mongoTemplate;

    private OrderResponseProducer orderResponseProducer;

    @Autowired
    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate, OrderResponseProducer orderResponseProducer) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
        this.orderResponseProducer = orderResponseProducer;
    }

    public ResponseEntity<Object> getOrderQuantityByCustomerId(Long customerId) {
        try {
            return ResponseEntity.ok().body(mongoTemplate.
                    find(Query.query(Criteria.where("customerId").is(customerId)),
                            Order.class).size());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public void insertOrder(Order order) {
        try {
            log.info("Inserted Order: " + order);
            orderRepository.insert(order);
            orderResponseProducer.sendOrderResponseMessage(
                    generateResponse(String.format("Order with id: %s", order.getOrderId()),200));
        } catch (Exception e) {
            orderResponseProducer.sendOrderResponseMessage(generateResponse(e.getMessage(), 401));
        }
    }

    public List<Order> findOrders() {
        return mongoTemplate.findAll(Order.class);
    }

    private Response generateResponse(String message, Integer statusCode) {
        return Response.builder()
                .description(message)
                .responseDate(LocalDateTime.now())
                .statusCode(statusCode)
                .build();
    }

    public ResponseEntity<Object> getTotalValuePerOrderId(UUID orderId) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(orderId));
            query.fields().include("finalPrice").exclude("_id");

            return ResponseEntity.ok().body(mongoTemplate.findOne(query, Order.class).getFinalPrice());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<List<Order>> getAllOrdersPerCustomer(Long customerId) throws Exception {
        try {
            return ResponseEntity.ok().body(mongoTemplate.
                    find(Query.query(Criteria.where("customerId").is(customerId)),
                            Order.class));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
