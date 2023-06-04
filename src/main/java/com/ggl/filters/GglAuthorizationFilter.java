package com.ggl.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GglAuthorizationFilter implements WebFilter{
    private static String REGISTER_PATH="/register";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().equals(REGISTER_PATH)) {
            return chain.filter(exchange);
        }
        return chain.filter(exchange);

    }
    
}
