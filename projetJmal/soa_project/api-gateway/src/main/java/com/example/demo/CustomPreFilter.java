package com.example.demo;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomPreFilter extends AbstractGatewayFilterFactory<CustomPreFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(CustomPreFilter.class);

    public CustomPreFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Pre-filter logic
            logger.info("Pre Filter: " + exchange.getRequest().getHeaders().getFirst("Authorization"));

            // Continue with the filter chain
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties for the filter
    }
}
