package com.example.webfluxexample.repository;

import com.example.webfluxexample.entity.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ItemRepository extends ReactiveMongoRepository<Item, String> {
    Mono<Item> findByName(String name);
}
