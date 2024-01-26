package com.example.webfluxexample.handler;

import com.example.webfluxexample.model.ItemModel;
import com.example.webfluxexample.model.SubItemModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class ItemHandler {
    public Mono<ServerResponse> getAllItem(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just(
                        new ItemModel(UUID.randomUUID().toString(), "Name 1", 10, Collections.emptyList()),
                        new ItemModel(UUID.randomUUID().toString(), "Name 2", 20, List.of(
                                new SubItemModel("SubItem 1", BigDecimal.valueOf(1001)),
                                new SubItemModel("SubItem 2", BigDecimal.valueOf(2001))
                        ))), ItemModel.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(
                                new ItemModel(serverRequest.pathVariable("id"),
                                        "Item name 1", 10, Collections.emptyList())),
                        ItemModel.class);
    }

    public Mono<ServerResponse> createItem(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ItemModel.class)
                .flatMap(item -> {
                    log.info("Item for create: {}", item);
                    return Mono.just(item);
                })
                .flatMap(item -> ServerResponse.created(URI.create("/api/v1/functions/item/" + item.getId())).build());
    }

    public Mono<ServerResponse> errorRequest(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.error(new RuntimeException("Exception in errorRequest")), String.class)
                .onErrorResume(ex -> {
                    log.error("Error in errorRequest", ex);
                    return ServerResponse.badRequest().body(Mono.error(ex), String.class);
                });
    }
}
