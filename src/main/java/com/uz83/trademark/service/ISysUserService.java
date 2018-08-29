package com.uz83.trademark.service;

import com.uz83.trademark.model.SysUser;
import com.uz83.trademark.model.SysUserExample;

public interface ISysUserService extends IBaseService<SysUser, Integer, SysUserExample> {

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     * @throws Exception
     */
    SysUser getByUsername(String username) throws Exception;
}
