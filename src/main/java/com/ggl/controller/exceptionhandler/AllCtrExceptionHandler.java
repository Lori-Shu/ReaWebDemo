package com.ggl.controller.exceptionhandler;


import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.view.View;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

@ControllerAdvice({"com.ggl.controller"})
@Slf4j
public class AllCtrExceptionHandler{
    @ExceptionHandler({ Throwable.class })
  public Mono<String> handleGlobalException(Exception e) {
      log.warn("globalException" + e.getMessage());
      return Mono.just("error/404");
    
  }
  
}