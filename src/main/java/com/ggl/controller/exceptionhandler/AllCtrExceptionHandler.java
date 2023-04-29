package com.ggl.controller.exceptionhandler;


import java.nio.charset.StandardCharsets;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

@ControllerAdvice("com.ggl.controller")
@Slf4j
public class AllCtrExceptionHandler{
    @ExceptionHandler({ Exception.class })
  @ResponseBody
  public String handleGlobalException(Exception e) {
      log.warn("globalException" + e.getMessage());
      return e.getMessage();
    
  }
  
}