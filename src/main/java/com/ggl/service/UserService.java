package com.ggl.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;

import com.ggl.entity.CommonResult;
import com.ggl.entity.User;

import reactor.core.publisher.Mono;

public interface UserService {
    public CompletableFuture<CommonResult<String>> register(User u);

    public CompletableFuture<DataBuffer> login(ServerWebExchange exchange);
    
    public CompletableFuture<List<User>> findListByUsername(User u);
}
