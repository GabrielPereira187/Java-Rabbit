package br.com.rabbit;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableRabbit
public class RabbitProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitProjectApplication.class, args);
	}

}
