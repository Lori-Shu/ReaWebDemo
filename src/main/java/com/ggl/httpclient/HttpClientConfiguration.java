package com.ggl.httpclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfiguration {
  @Bean
  public TestHttpClient testHttpClient() {
WebClient client = WebClient.builder().baseUrl("http://cn.bing.com").build();
HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();

TestHttpClient service = factory.createClient(TestHttpClient.class);
return service;
  }
  
}
