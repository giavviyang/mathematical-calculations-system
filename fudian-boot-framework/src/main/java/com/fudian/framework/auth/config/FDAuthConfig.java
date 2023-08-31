package com.fudian.framework.auth.config;

import com.fudian.framework.auth.interceptor.FDAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description 浮点全宗拦截认证配置类
 * @ClassName MyWebConfigurer
 * @Author mr.hou
 * @Date 2019/12/25 12:15 下午
 * @Version 1.0.0
 **/
@Configuration
public class FDAuthConfig implements WebMvcConfigurer {

    @Bean
    public FDAuthInterceptor fDAuthInterceptor() {
        return new FDAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fDAuthInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/static/**");
    }
}
