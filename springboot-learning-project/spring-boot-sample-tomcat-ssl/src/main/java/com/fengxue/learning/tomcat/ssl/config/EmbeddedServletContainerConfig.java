package com.fengxue.learning.tomcat.ssl.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.Ssl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedServletContainerConfig {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                Ssl ssl = new Ssl();
                ssl.setKeyStore("classpath:sample.jks");
                ssl.setKeyPassword("password");
                ssl.setKeyStorePassword("secret");
                container.setSsl(ssl);
                container.setPort(7001);
            }
        };
    }



}
