package com.uz83.trademark.mapper;

import com.uz83.trademark.model.SysUserRole;
import com.uz83.trademark.model.SysUserRoleExample;
import org.springframework.stereotype.Repository;

/**
 * SysUserRoleDAO继承基类
 */
@Repository
public interface SysUserRoleDAO extends MyBatisBaseDao<SysUserRole, Integer, SysUserRoleExample> {
}