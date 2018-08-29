package com.uz83.trademark.security;

import com.uz83.trademark.model.SysRole;
import com.uz83.trademark.model.SysUser;
import com.uz83.trademark.service.ISysUserRoleService;
import com.uz83.trademark.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * @author panyh
 * @date 2018/8/16 13:55
 * 用户身份认证服务类
 */
@Service("userDetailsService")
public class AuthUserDetailService implements UserDetailsService {

    @Value("${uz83.system.admin.account}")
    private String adminAccount;

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // 如果是系统生成的超级管理就需要不查数据库了
        if (StringUtils.isNotEmpty(adminAccount) && adminAccount.equals(name)) {
            return null;
        }

        UserDetails userDetails = null;
        try {
            SysUser user = sysUserService.getByUsername(name);
            if (user != null) {
                List<SysRole> urs = sysUserRoleService.findRoleByUser(user.getId());
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                if (urs != null && !urs.isEmpty()) {
                    for (SysRole role : urs) {
                        SimpleGrantedAuthority grant = new SimpleGrantedAuthority(role.getAlias());
                        authorities.add(grant);
                    }
                }
                //封装自定义UserDetails类
                userDetails = new MyUserDetails(user, authorities);
            } else {
                throw new UsernameNotFoundException("该用户不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }

}
