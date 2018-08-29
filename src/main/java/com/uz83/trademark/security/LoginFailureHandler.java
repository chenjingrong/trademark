package com.uz83.trademark.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/*
 * @author panyh
 * @date 2018/8/16 14:43
 */
@Service("loginFailureHandler")
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
            throws IOException, ServletException {
        System.out.println("用户登录失败 --- error" + authenticationException.getMessage());
//        response.sendRedirect("/login?error=" + authenticationException.getMessage());
        String error = authenticationException.getClass().getSimpleName();
        request.getRequestDispatcher("/login?error=" + error).forward(request,response);
    }

}
