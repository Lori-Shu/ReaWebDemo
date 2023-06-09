package com.ggl.filters;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DataBufferWrapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.entity.CommonResult;
import com.ggl.entity.User;
import com.ggl.service.UserService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GglAuthenticationFilter implements WebFilter{
    private static String LOGIN_PATH="/login";
    private PasswordEncoder gglPasswordEncoder;
    private UserService userService;
    private ObjectMapper objectMapper;
    public GglAuthenticationFilter(PasswordEncoder e,UserService u,ObjectMapper o){
        gglPasswordEncoder=e;
        userService=u;
        objectMapper=o;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("authenticate request uri"+exchange.getRequest().getURI().getPath());
        if(!(exchange.getRequest().getURI().getPath().equals(LOGIN_PATH))){
            
            return chain.filter(exchange);
        }
        ServerHttpResponse response=exchange.getResponse();
        return response.writeWith(Mono.fromFuture(userService.login(exchange)));
    }
    
    
}
