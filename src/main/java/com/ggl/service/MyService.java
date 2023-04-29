package com.ggl.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.entity.User;
import com.ggl.httpclient.BingRequestClient;
import com.ggl.httpclient.TestHttpClient;
import com.ggl.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MyService {
  private TestHttpClient testHttpClient;
  private ObjectMapper objectMapper;
  private UserRepository userRepository;
  private BingRequestClient bingRequestClient;

  public MyService(TestHttpClient testHttpClient,ObjectMapper objectMapper,UserRepository userRepository,
      BingRequestClient bingRequestClient) {
    this.testHttpClient = testHttpClient;
    this.objectMapper = objectMapper;
    this.userRepository = userRepository;
    this.bingRequestClient = bingRequestClient;
  }
  @Async
  public CompletableFuture<String> printHtml(String param) throws JsonProcessingException {
    Mono<String> htmlStr = testHttpClient.getHtmlStr();
    log.warn("param" + param);
    // log.warn(htmlStr.block());
    // throw new RuntimeException("故意生成的异常");
    return CompletableFuture.completedFuture(htmlStr.block());
  }
    @Async
    public void testJson() {
      Optional<User> findById = userRepository.findById("3596");
      try {
        log.warn(objectMapper.writeValueAsString(findById.get()));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    @Async
    public void testBingRequest() {
      String mainPage = bingRequestClient.getMainPage("hello world!");
      log.warn(mainPage);
    }
    @Async
    public CompletableFuture<String> addUserTestRollBack(User user) {
      User save = userRepository.save(user);
      String mainPage = bingRequestClient.getMainPage("hello world!");
      return CompletableFuture.completedFuture("添加成功");
    }
  
  
}
