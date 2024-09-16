package br.com.rabbit.repositories;

import br.com.rabbit.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends MongoRepository<Order, UUID> {

    @Query(value = "{customerId: 0?}", count = true)
    Long countCustomerId(Long customerId);


}
