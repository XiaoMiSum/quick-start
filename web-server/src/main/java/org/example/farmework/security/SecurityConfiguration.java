package org.example.farmework.security;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import xyz.migoo.framework.web.config.WebProperties;

@Configuration
public class SecurityConfiguration {

    @Resource
    private WebProperties webProperties;

    /**
     * 项目自定义 权限规则，定义该bean主要是框架中有 使用到该对象，否则无法启动系统
     *
     * @return 权限规则配置
     */
    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeRequestsCustomizer() {
        return registry -> registry.requestMatchers(api("/signup"), api("/registry"), api("/config"),
                api("/authenticator"), api("/tools/**"), api("/open/**"), api("/api-docs")).permitAll();
    }

    private String api(String url) {
        return webProperties.getApiPrefix() + url;
    }

}
