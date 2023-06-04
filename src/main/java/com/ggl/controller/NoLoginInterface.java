package com.ggl.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ggl.entity.CommonResult;
import com.ggl.entity.User;
import com.ggl.service.UserService;

import reactor.core.publisher.Mono;

@RestController
public class NoLoginInterface {
    private UserService userService;
    public NoLoginInterface(UserService u){
        userService=u;
    }
    @PostMapping("/register")
    public Mono<CommonResult<String>> register(@RequestBody User user){
        CompletableFuture<CommonResult<String>> res = userService.register(user);
        return Mono.fromFuture(res);
    }
}
