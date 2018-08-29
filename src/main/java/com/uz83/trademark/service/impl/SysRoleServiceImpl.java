package com.uz83.trademark.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.uz83.trademark.mapper.SysRoleDAO;
import com.uz83.trademark.model.SysRole;
import com.uz83.trademark.model.SysRoleExample;
import com.uz83.trademark.service.ISysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
 * @author panyh
 * @date 2018/8/17 13:12
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Resource
    private SysRoleDAO sysRoleDAO;

    public List<SysRole> findAll() {
        return sysRoleDAO.selectByExample(new SysRoleExample());
    }

    public void saveOrUpdate(String model, String url, JSONArray roles) {
        SysRole role = null;
        SysRoleExample example = new SysRoleExample();
        for (int i = 0; i < roles.size(); i++) {
            boolean add = true;
            String alias = roles.getString(i);
            example.clear();
            example.createCriteria().andAliasEqualTo(alias);
            List<SysRole> list = sysRoleDAO.selectByExample(example);
            if (list != null && !list.isEmpty()) {
                add = false;
                role = list.get(0);
            } else {
                role = new SysRole();
            }
            role.setName("role." + alias + ".name");
            role.setDesc("role." + alias + ".desc");
            role.setAlias(alias);
            role.setModel(model);
            role.setUrl(url);

            if (add) {
                sysRoleDAO.insert(role);
            } else {
                sysRoleDAO.updateByPrimaryKey(role);
            }
        }
    }

}
