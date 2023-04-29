package com.ggl.controller;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ggl.entity.User;
import com.ggl.repository.UserRepository;
import com.ggl.service.MyService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {
  private Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
  private UserRepository userRepository;
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;
  private MyService myService;

  public HelloWorldController(UserRepository userRepository,
      ThreadPoolTaskExecutor threadPoolTaskExecutor, MyService myService) {

    this.userRepository = userRepository;
    this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    this.myService = myService;
  }

  @PostMapping("getUser")
  public Mono<User> getuser(@RequestBody String id) {
    logger.warn("id==" + id);
    User user = new User();
    user.setId(id);
    user.setAge("22");
    user.setName("zko");
    user.setSex(true);
    Optional<User> userEt = userRepository.findById(id);
    return Mono.just(userEt.orElse(new User()));
  }

  @PostMapping("addUser")
  public Mono<String> addUserTestRollBack(@RequestBody User user) {
    logger.warn("进入添加用户方法");
     CompletableFuture<String> future=myService.addUserTestRollBack(user);
    return Mono.fromFuture(future);

  }

  @PostMapping("getBaidu")
  public Mono<String> getBaidu(@RequestBody String par) throws JsonProcessingException {
    logger.warn("接收参数==" + par);
    logger.warn("===================");
    // threadPoolTaskExecutor.submit(()->{
    // System.out.println(htmlStr.block());
    // });
    CompletableFuture<String> printHtml = myService.printHtml(par);

    return Mono.fromFuture(printHtml);

  }

  @PostMapping("say")
  public String say() {
    return "hello";
  }

  @PostMapping("testJson")
  public void testJson() {
    myService.testJson();
  }
  
  @PostMapping("testBIng")
  public void testBing() {
    myService.testBingRequest();
  }
  @GetMapping("getWorld")
  public Mono<String> getWorld(){
    throw new RuntimeException("testEc");
    // return Mono.just("this is world!");
  }
}
