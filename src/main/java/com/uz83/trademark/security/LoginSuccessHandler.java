package com.uz83.trademark.security;

import com.uz83.trademark.service.ISysUserService;
import com.uz83.trademark.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @author panyh
 * @date 2018/8/16 14:42
 */
@Service("loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ISysUserService sysUserService;

    @Value("${uz83.system.admin.account}")
    private String adminAccount;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
            throws IOException, ServletException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            if (logger.isDebugEnabled()) {
                logger.debug("用户登录成功 --- loginUser:" + user);
            }
            if (!user.getUsername().equals(adminAccount)) {
                //维护在session中
                try {
                    request.getSession().setAttribute(UserUtil.LOGIN_USER_KEY, sysUserService.getByUsername(user.getUsername()));
                } catch (Exception e) {
                    logger.error("{}", e);
                }
            }
            response.sendRedirect("/");
        }
    }
}
