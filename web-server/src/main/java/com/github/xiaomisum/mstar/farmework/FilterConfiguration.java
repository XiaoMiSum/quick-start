package com.github.xiaomisum.mstar.farmework;

import cn.hutool.extra.spring.SpringUtil;
import com.github.xiaomisum.mstar.farmework.filter.UserLastProjectFilter;
import com.github.xiaomisum.mstar.service.sys.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.migoo.framework.web.config.WebProperties;

@Configuration
@AutoConfigureAfter(UserService.class)
public class FilterConfiguration {

    @Resource
    private WebProperties properties;

    @Bean
    public FilterRegistrationBean<UserLastProjectFilter> registerFilter() {
        FilterRegistrationBean<UserLastProjectFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserLastProjectFilter(SpringUtil.getBean(UserService.class)));
        registrationBean.addUrlPatterns(
                api("/project/*"), api("/track/*")
        );
        registrationBean.setName("UserLastProjectFilter");
        return registrationBean;
    }

    private String api(String api) {
        return properties.getApiPrefix() + api;
    }
}
