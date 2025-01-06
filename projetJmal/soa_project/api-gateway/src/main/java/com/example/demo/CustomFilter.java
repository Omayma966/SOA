package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
@Order(1)
public class CustomFilter implements GlobalFilter {

    Logger logger = LoggerFactory.getLogger(CustomFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Pre-filter logic
        logger.info("Pre Filter: " + exchange.getRequest().getURI());

        // Continue with the filter chain
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Post-filter logic
            logger.info("Post Filter: " + exchange.getResponse().getStatusCode());
        }));
    }
}
