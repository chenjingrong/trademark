package com.uz83.trademark.service;

import com.alibaba.fastjson.JSONArray;
import com.uz83.trademark.model.SysRole;

import java.util.List;

/*
 * @author panyh
 * @date 2018/8/17 13:12
 */
public interface ISysRoleService {

    /**
     * 获取所有角色列表
     *
     * @return
     */
    public List<SysRole> findAll();

    /**
     * 增加或更新角色
     *
     * @param model
     * @param url
     * @param roles
     */
    public void saveOrUpdate(String model, String url, JSONArray roles);
}
