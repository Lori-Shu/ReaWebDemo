package com.ggl.service;

import java.util.concurrent.CompletableFuture;

import com.ggl.entity.CommonResult;
import com.ggl.entity.User;

public interface UserService {
    public CompletableFuture<CommonResult<String>> register(User u);
    
    public CompletableFuture<CommonResult<User>> findOne(User u);
}
