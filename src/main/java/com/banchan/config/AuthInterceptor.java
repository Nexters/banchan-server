package com.banchan.config;

import com.banchan.config.annotation.BanchanAuth;
import com.banchan.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuthInterceptor extends WebContentInterceptor {

    @Autowired private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        if (handler instanceof HandlerMethod) {
            BanchanAuth authProcessing = ((HandlerMethod) handler).getMethodAnnotation(BanchanAuth.class);

            return Optional.ofNullable(authProcessing).map( auth -> {
                final Long id = Long.valueOf(request.getHeader("x-Id"));
                final String token = request.getHeader("x-token");
                try {
                    return userService.auth(id, token) && super.preHandle(request, response, handler);
                } catch (ServletException e) {
                    e.printStackTrace();
                    return false;
                }
            }).orElse(super.preHandle(request, response, handler));

        } else {
            return super.preHandle(request, response, handler);
        }
    }
}
