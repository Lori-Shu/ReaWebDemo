package com.ggl.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.entity.CommonResult;
import com.ggl.entity.User;
import com.ggl.repository.UserRepository;
import com.ggl.service.UserService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private PasswordEncoder gglPasswordEncoder;
    private ObjectMapper objectMapper;
    public UserServiceImpl(UserRepository u,PasswordEncoder e,ObjectMapper o){
        userRepository=u;
        gglPasswordEncoder=e;
        objectMapper=o;
    }

    @Override
    @Async
    public CompletableFuture<List<User>> findListByUsername(User u) {
        try {
            List<User> users = userRepository.findAllByUsername(u.getUsername());
            return CompletableFuture.completedFuture(users);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Async
    public CompletableFuture<CommonResult<String>> register(User u) {
        try {
            Assert.isTrue("".equals(u.getId()), "this user has an id ,may has been registered");
            Assert.isTrue(!"".equals(u.getUsername()), "username cannot be empty");
            Assert.isTrue(!"".equals(u.getPassword()), "password cannot be empty");
            u.setPassword(gglPasswordEncoder.encode(u.getPassword()));
            userRepository.save(u);
        } catch (Exception e) {
            throw e;
        }
        return CompletableFuture.completedFuture(CommonResult.success("register success"));
    }

    @Override
    @Async
    public CompletableFuture<DataBuffer> login(ServerWebExchange exchange){
          try {
            Flux<DataBuffer> bd = exchange.getRequest().getBody();
            Mono<DataBuffer> single = bd.single();
            DataBuffer dataBuffer=single.block();
            byte[] bodyBytes=new byte[dataBuffer.readableByteCount()];
            log.info("第一个databuffer可读长度"+ dataBuffer.readableByteCount());
            dataBuffer.read(bodyBytes);
            User usr = objectMapper.readValue(bodyBytes, User.class);
            
            CompletableFuture<List<User>> users = findListByUsername(usr);
            /**
             * 处理密码校验和返回
             */
            ServerHttpResponse response = exchange.getResponse();
            for(User one:users.get()){
            if (gglPasswordEncoder.matches(usr.getPassword(), one.getPassword())) {
                CommonResult<User> res = CommonResult.success(one);
                byte[] resBytes= objectMapper.writeValueAsBytes(res);
                return CompletableFuture.completedFuture(response.bufferFactory().wrap(resBytes));    
            }
            }
            CommonResult<String> res = CommonResult.error("登录失败，请检查登录信息！");
            byte[] resBytes= objectMapper.writeValueAsBytes(res);
            return CompletableFuture.completedFuture(response.bufferFactory().wrap(resBytes));
            }catch(Exception e){
            ServerHttpResponse response = exchange.getResponse();
            log.error(e.getMessage());
            return CompletableFuture.completedFuture(response.bufferFactory().wrap("{\"code\":400,\"data\":\"\"}".getBytes()));
        }
            
    }
}
