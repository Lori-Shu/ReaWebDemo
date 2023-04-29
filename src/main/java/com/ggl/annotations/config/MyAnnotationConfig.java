package com.ggl.annotations.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(MyWebClientAnnoInterfaceImport.class)
@Configuration
public class MyAnnotationConfig {
    
}
