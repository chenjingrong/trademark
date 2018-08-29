package com.uz83.trademark.config;

import com.uz83.trademark.security.auth.MyFilterSecurityInterceptor;
import com.uz83.trademark.util.DesignUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import java.util.UUID;

/*
 * @author panyh
 * @date 2018/8/16 13:40
 * 权限配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${uz83.system.admin.account}")
    private String adminAccount;

    @Resource
    private MyFilterSecurityInterceptor mySecurityFilter;

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private AuthenticationProvider securityProvider;

    @Resource
    private AuthenticationSuccessHandler loginSuccessHandler;
    @Resource
    private AuthenticationFailureHandler loginFailureHandler;

    @Override
    protected UserDetailsService userDetailsService() {
        //自定义用户信息类
        return this.userDetailsService;
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterBefore(mySecurityFilter, FilterSecurityInterceptor.class) // 在正确的位置添加我们自定义的过滤器
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/", true)
                .successHandler(loginSuccessHandler) // 登录成功处理逻辑
                .failureHandler(loginFailureHandler) // 登录失败处理逻辑
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and().httpBasic();
    }

    /**
     * 创建一个超级管理员
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 按规则生成超级管理员
        if (StringUtils.isNotEmpty(adminAccount)) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(password);
            // 超级管理员，拥有所有权限
            auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser(adminAccount)
                    .password(passwordEncoder.encode(password)).authorities(DesignUtil.getAllRoleAliass().toArray(new String[]{}));
        }

        // 自定义AuthenticationProvider
        auth.authenticationProvider(securityProvider);
    }

}
