package com.uz83.trademark.mapper;

import com.uz83.trademark.model.SysUser;
import com.uz83.trademark.model.SysUserExample;
import org.springframework.stereotype.Repository;

/**
 * SysUserDAO继承基类
 */
@Repository
public interface SysUserDAO extends MyBatisBaseDao<SysUser, Integer, SysUserExample> {
}