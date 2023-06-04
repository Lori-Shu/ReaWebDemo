package com.ggl.filters;

import java.util.concurrent.CompletableFuture;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DataBufferWrapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.entity.CommonResult;
import com.ggl.entity.User;
import com.ggl.service.UserService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
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
        if(!(exchange.getRequest().getURI().getPath().equals(LOGIN_PATH))){
            
            return chain.filter(exchange);
        }
        try {
            DataBuffer buffer = exchange.getRequest().getBody().blockFirst();
            byte[] buf = new byte[buffer.readableByteCount()];
            buffer.read(buf);
            User usr = objectMapper.convertValue(new String(buf), User.class);
            CompletableFuture<CommonResult<User>> one = userService.findOne(usr);
            /**
             * 处理密码校验和返回
             */
            ServerHttpResponse response = exchange.getResponse();
            if (gglPasswordEncoder.matches(usr.getPassword(), one.get().getData().getPassword())) {
                response.writeWith(Mono.just(response.bufferFactory().wrap("登录成功！".getBytes())));
                return chain.filter(exchange);    
            }
            response.writeWith(Mono.just(response.bufferFactory().wrap("密码错误！".getBytes())));
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error(e.getMessage());
            ServerHttpResponse response = exchange.getResponse();
            response.writeWith(Mono.just(response.bufferFactory().wrap("登录过程出现异常！".getBytes())));
            return chain.filter(exchange);
        }
        

    }
    
}
