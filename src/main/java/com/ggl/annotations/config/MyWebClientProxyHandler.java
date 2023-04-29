package com.ggl.annotations.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyWebClientProxyHandler implements InvocationHandler {

    private RestTemplate restTemplate;
    private String baseUrl;

    public MyWebClientProxyHandler(String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplateBuilder().defaultHeader("content-type", "application/json").build();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(PostMapping.class)) {
                PostMapping getMapping = (PostMapping) annotation;
                // Map<String, Object> attributes =
                // annotationMetadata.getAnnotationAttributes(GetMapping.class
                // .getCanonicalName());
                String url = getMapping.value()[0];
                Class<?> returnType = method.getReturnType();
                log.warn("url:" + baseUrl+url + "  " + "return type:" + returnType.toString() + "  " + "body:"
                        + args[0].toString());
                Object postForObject = restTemplate.postForObject(baseUrl+url, args, returnType, "");
                return postForObject;
            }
        }
        return null;
        // throw new RuntimeException("mywebclient only support post request");
    }

}
