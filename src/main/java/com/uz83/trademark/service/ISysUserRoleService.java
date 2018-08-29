package com.uz83.trademark.service;

import com.uz83.trademark.model.SysRole;
import com.uz83.trademark.model.SysUser;

import java.util.List;

/*
 * @author panyh
 * @date 2018/8/16 14:18
 */
public interface ISysUserRoleService {

    /**
     * 根据角色ID查询用户
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    List<SysUser> findUserByRole(int roleId) throws Exception;

    /**
     * 根据用户ID查询角色
     *
     * @param userId
     * @return
     * @throws Exception
     */
    List<SysRole> findRoleByUser(int userId) throws Exception;

}
