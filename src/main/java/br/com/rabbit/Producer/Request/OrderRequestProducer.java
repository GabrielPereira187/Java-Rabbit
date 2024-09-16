package br.com.rabbit.Producer.Request;

import br.com.rabbit.entities.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderRequestProducer {

    private AmqpTemplate amqpTemplate;

    @Autowired
    public OrderRequestProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseEntity<Object> sendOrderMessage(Order order) throws JsonProcessingException {
        try {
            amqpTemplate.convertAndSend("orderRequestExchange", "orderRequest-rout-key",
                    order);
            log.info("Message sent: " + order);
            return ResponseEntity.ok().body("Sucesso no envio");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro no envio");
        }
    }

}
