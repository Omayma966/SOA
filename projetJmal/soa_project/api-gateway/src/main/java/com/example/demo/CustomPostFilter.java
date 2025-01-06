package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;

@Component
public class CustomPostFilter extends AbstractGatewayFilterFactory<CustomPostFilter.Config> {

    public CustomPostFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Custom post-filter logic here
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties for the filter (if any)
    }
}
