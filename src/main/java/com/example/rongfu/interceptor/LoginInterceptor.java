package com.example.rongfu.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器（指定拦截的处理逻辑）
 */
public class LoginInterceptor implements HandlerInterceptor {
    //preHandle，在服务器处理所有的请求之前，



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //if (request.getRequestURL().equals("/login.html")) return true;
        if (request.getSession().getAttribute("userId") == null) {
            //若未登录,转向登录页面
            response.sendRedirect("/login.html");
            return false;
        }
        return true;
    }
}
