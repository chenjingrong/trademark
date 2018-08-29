package com.uz83.trademark.service.impl;

import com.uz83.trademark.mapper.SysRoleDAO;
import com.uz83.trademark.mapper.SysUserDAO;
import com.uz83.trademark.mapper.SysUserRoleDAO;
import com.uz83.trademark.model.*;
import com.uz83.trademark.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @author panyh
 * @date 2018/8/16 14:18
 */
@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {
    @Resource
    private SysUserRoleDAO sysUserRoleDAO;

    @Resource
    private SysUserDAO sysUserDAO;

    @Resource
    private SysRoleDAO sysRoleDAO;

    /**
     * 根据角色ID查询用户
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    public List<SysUser> findUserByRole(int roleId) throws Exception {
        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<SysUserRole> list = sysUserRoleDAO.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            List<Integer> userIds = list.stream().map(SysUserRole::getUserId).collect(Collectors.toList());

            SysUserExample userExample = new SysUserExample();
            userExample.createCriteria().andIdIn(userIds);
            return sysUserDAO.selectByExample(userExample);
        }
        return null;
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<SysRole> findRoleByUser(int userId) throws Exception {
        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<SysUserRole> list = sysUserRoleDAO.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            List<Integer> roleIds = list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

            SysRoleExample roleExample = new SysRoleExample();
            roleExample.createCriteria().andIdIn(roleIds);
            return sysRoleDAO.selectByExample(roleExample);
        }
        return null;
    }
}
