package com.ek.serialsserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Eduard on 14.05.2016.
 * Configure application properties
 */
@Configuration
@PropertySource("classpath:resources/app.properties")
public class PropertiesConfiguration {

    @Value("${app.debug}")
    private Boolean isDebug;

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        AppProperties appProperties = new AppProperties();
        appProperties.setDebug(isDebug);

        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("com.ek.serialsserver.index.routes.IndexRoutes.setAppProperties");
        bean.setArguments(new Object[]{appProperties});

        return bean;
    }
}
