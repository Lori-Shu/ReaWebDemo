package com.ggl.annotations.config;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ggl.App;
import com.ggl.annotations.MyWebClient;


public class MyWebClientAnnoInterfaceImport implements ImportBeanDefinitionRegistrar,
ResourceLoaderAware, EnvironmentAware {
  /**
     * 资源加载器
     */
    private ResourceLoader resourceLoader;
    /**
     * 环境
     */
    private Environment environment;

    // private MyWebClientProxyHandler myWebClientProxyHandler;

    // public MyWebClientAnnoConfig(MyWebClientProxyHandler myWebClientProxyHandler) {
    //     this.myWebClientProxyHandler = myWebClientProxyHandler;
    // }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        // 创建scanner
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(resourceLoader);

        // 设置扫描器scanner扫描的过滤条件
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(MyWebClient.class);
        scanner.addIncludeFilter(annotationTypeFilter);

        // 获取指定要扫描的basePackages
        Set<String> basePackages = getBasePackages(metadata);

        // 遍历每一个basePackages
        for (String basePackage : basePackages) {
            // 通过scanner获取basePackage下的候选类(有标@SimpleRpcClient注解的类)
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            // 遍历每一个候选类，如果符合条件就把他们注册到容器
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition) {
                    // verify annotated class is an interface
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                    Assert.isTrue(annotationMetadata.isInterface(), "@MyWebClient can only be specified on an interface");
                    // 获取注解的属性
                    Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(MyWebClient.class .getCanonicalName());
                    // 注册到容器
                    try {
                        registerMyWebClient(registry, annotationMetadata, attributes);
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
  
  /**
     * 利用factoryBean创建代理对象，并注册到容器
 * @throws ClassNotFoundException
     */
    private void registerMyWebClient(BeanDefinitionRegistry registry,
                                                AnnotationMetadata annotationMetadata,
                                                Map<String, Object> attributes) throws ClassNotFoundException {
        // 类名（接口全限定名）
        String className = annotationMetadata.getClassName();
        // 创建MyWebClientProxyFactoryBean的BeanDefinition
        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MyWebClientProxyBean.class);
        // // 解析出@SimpleRpcClient注解的name
        // String name = getName(attributes);
        // if (!StringUtils.hasText(name)) {
        //     throw new ProviderNameNullException(String.format("class [%s] , @SimpleRpcClient name or value can not be null, please check.", className));
        // }

        // 给factoryBean添加属性值
        // definition.addPropertyValue("name", name);
        definitionBuilder.addConstructorArgValue(Class.forName(annotationMetadata.getClassName()));
        definitionBuilder.addConstructorArgValue(Class.forName(annotationMetadata.getClassName()));
        definitionBuilder.addConstructorArgValue(attributes.get("value"));
        // definition.addPropertyValue("handler", myWebClientProxyHandler);
        definitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        // String alias = name + "SimpleRpcClient";
        AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();

        // 注册bean定义信息到容器
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, null);
      	// 使用BeanDefinitionReaderUtils工具类将BeanDefinition注册到容器
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    /**
     * 创建扫描器
     */
    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    /**
     * 获取base packages
     */
    protected static Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        // // 获取到@EnableSimpleRpcClients注解所有属性
        // Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableSimpleRpcClients.class.getCanonicalName());
        // Set<String> basePackages = new HashSet<>();
        // assert attributes != null;
        // // value 属性是否有配置值，如果有则添加
        // for (String pkg : (String[]) attributes.get("value")) {
        //     if (StringUtils.hasText(pkg)) {
        //         basePackages.add(pkg);
        //     }
        // }

        // // basePackages 属性是否有配置值，如果有则添加
        // for (String pkg : (String[]) attributes.get("basePackages")) {
        //     if (StringUtils.hasText(pkg)) {
        //         basePackages.add(pkg);
        //     }
        // }

        // 如果上面两步都没有获取到basePackages，那么这里就默认使用当前项目启动类所在的包为basePackages
        // if (basePackages.isEmpty()) {
        //     basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        // }
// 扫描启动类及以下的所有包
        Set<String> basePackages = new HashSet<>();
        basePackages.add(ClassUtils.getPackageName(App.class.getName()));
        return basePackages;
    }

  
  @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}