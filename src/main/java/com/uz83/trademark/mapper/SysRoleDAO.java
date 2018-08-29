package com.uz83.trademark.mapper;

import com.uz83.trademark.model.SysRole;
import com.uz83.trademark.model.SysRoleExample;
import org.springframework.stereotype.Repository;

/**
 * SysRoleDAO继承基类
 */
@Repository
public interface SysRoleDAO extends MyBatisBaseDao<SysRole, Integer, SysRoleExample> {
}