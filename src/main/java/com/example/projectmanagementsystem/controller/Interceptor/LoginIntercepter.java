package com.example.projectmanagementsystem.controller.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user == null || user.equals("")) {  // 没有user信息，即未登录
            response.sendRedirect("/index.html");
            return false;
        }

        return true;    // 已登录
    }
}
