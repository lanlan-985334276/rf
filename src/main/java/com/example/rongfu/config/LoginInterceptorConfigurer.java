package com.example.rongfu.config;

import com.example.rongfu.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器配置类（指定哪些请求直接放行，哪些请求需要进行拦截处理）
 */

@Configuration //该注解表示该类是一个配置类
public class LoginInterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建一个登录拦截器对象
        LoginInterceptor loginInterceptor = new LoginInterceptor();

        //白名单 ，不需要进行拦截的
        List<String> patterns = new ArrayList<>();
        patterns.add("/login.html");
        patterns.add("/register.html");
        patterns.add("/error");
        patterns.add("/css/**");
        patterns.add("/fonts/**");
        patterns.add("/img/**");
        patterns.add("/js/**");
        patterns.add("/plugins/fullavatareditor/scripts/**");
        patterns.add("/users/login");
        patterns.add("/users/**");
        patterns.add("/home.html");
        //利用注册工具进行拦截器注册
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")  //需要拦截的，/**表示拦截所有的
                .excludePathPatterns(patterns);  //排除在外的
    }
}