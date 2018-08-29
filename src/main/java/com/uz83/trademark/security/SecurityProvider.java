package com.uz83.trademark.security;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * @author panyh
 * @date 2018/8/16 14:36
 * 自定义认证服务
 */
@Service("securityProvider")
public class SecurityProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SecurityProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authenticate) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authenticate;
        String username = token.getName();
        UserDetails userDetails = null;

        if (username != null) {
            userDetails = userDetailsService.loadUserByUsername(username);
        }

        if (userDetails == null) {
            throw new BadCredentialsException("用户名/密码无效");
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("用户已被禁用");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new LockedException("账号已过期");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("账号已被锁定");
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new LockedException("凭证已过期");
        }

        // 密码匹配
        String password = userDetails.getPassword();
        if (!passwordEncoder.matches((String) token.getCredentials(), password)) {
            throw new BadCredentialsException("用户名/密码无效");
        }

        //授权
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

}
