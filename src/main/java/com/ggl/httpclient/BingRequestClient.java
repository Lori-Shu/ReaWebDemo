package com.ggl.httpclient;

import org.springframework.web.bind.annotation.PostMapping;

import com.ggl.annotations.MyWebClient;

@MyWebClient("http://cn.bing.com/")
public interface BingRequestClient {
    @PostMapping("")
    String getMainPage(String str);
}
