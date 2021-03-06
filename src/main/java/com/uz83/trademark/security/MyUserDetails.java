package com.uz83.trademark.security;

import com.uz83.trademark.model.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/*
 * @author panyh
 * @date 2018/8/16 13:50
 * 自定义用户身份信息
 */
public class MyUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    // 用户信息
    private SysUser user;
    // 用户角色
    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(SysUser user, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    public SysUser getSysUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.user.getExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.user.getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.user.getExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.getState();
    }
}
