package com.ggl.service.impl;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ggl.entity.CommonResult;
import com.ggl.entity.User;
import com.ggl.repository.UserRepository;
import com.ggl.service.UserService;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    public UserServiceImpl(UserRepository u){
        userRepository=u;
    }

    @Override
    @Async
    public CompletableFuture<CommonResult<User>> findOne(User u) {
        try {
            Optional<User> uo = userRepository.findById(u.getId());
            Assert.isTrue(uo.isPresent(), "user do not exist");
            return CompletableFuture.completedFuture(CommonResult.success(uo.get()));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Async
    public CompletableFuture<CommonResult<String>> register(User u) {
        try {
            Assert.isTrue(u.getId().equals(""), "this user has an id ,may has been register");
            Assert.isTrue(!u.getName().equals(""), "username cannot be empty");
            Assert.isTrue(!u.getPassword().equals(""), "password cannot be empty");
            userRepository.save(u);
        } catch (Exception e) {
            throw e;
        }
        return CompletableFuture.completedFuture(CommonResult.success("register success"));
    }
    
}
