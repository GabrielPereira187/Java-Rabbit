package br.com.rabbit.Consumer.Response;

import br.com.rabbit.entities.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderResponseConsumer {

    @RabbitListener(queues = {"orderResponse-queue"})
    public Response receive(@Payload Response message) {
        log.info("Received message: " + message);
        return message;
    }
}
