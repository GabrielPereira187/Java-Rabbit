package br.com.rabbit.Producer.Response;


import br.com.rabbit.entities.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderResponseProducer {

    private AmqpTemplate amqpTemplate;

    @Autowired
    public OrderResponseProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    public ResponseEntity<Object> sendOrderResponseMessage(Response response) {
        try {
            amqpTemplate.convertAndSend("orderResponseExchange", "orderResponse-rout-key",
                    response);
            log.info("Message Response Sent:" + response);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }
}
