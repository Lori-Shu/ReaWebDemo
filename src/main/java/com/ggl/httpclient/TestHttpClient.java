package com.ggl.httpclient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;

import reactor.core.publisher.Mono;

public interface TestHttpClient {
  @GetExchange("/")
  Mono<String> getHtmlStr();
}
